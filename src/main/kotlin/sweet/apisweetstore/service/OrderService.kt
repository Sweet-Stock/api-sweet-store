package sweet.apisweetstore.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.OrderRequest
import sweet.apisweetstore.dto.response.OrderResponse
import sweet.apisweetstore.exception.FlowException
import sweet.apisweetstore.integration.IntegracaoSweetStock
import sweet.apisweetstore.model.Order
import sweet.apisweetstore.model.OrderItem
import sweet.apisweetstore.repository.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val paymentRepository: PaymentRepository,
    private val cartService: CartService,
    private val cartRepository: CartRepository,
    private val integracaoSweetStock: IntegracaoSweetStock,
    private val orderItemRepository: OrderItemRepository
) {

    fun createOrder(orderRequest: OrderRequest): ResponseEntity<OrderResponse> {
        val (uuidUser, idPayment) = orderRequest

        if (userRepository.countByUuid(uuidUser) != 1L) {
            return ResponseEntity.badRequest().build()
        }

        if (paymentRepository.countById(idPayment) != 1L) {
            return ResponseEntity.badRequest().build()
        }

        val payment = paymentRepository.findById(idPayment).get()

        if (payment.uuidUser != uuidUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val cartUserProducts = cartService.getUserCartByUuid(uuidUser).body
        val cartUserProductsOriginal = cartRepository.getUserCartByUuid(uuidUser)

        if (cartUserProducts?.itens?.size == 0) {
            return ResponseEntity.badRequest().build()
        }

        cartUserProducts?.itens?.forEach { product ->
            cartUserProductsOriginal.forEach { cartProduct ->
                if (cartProduct.uuidProduct == product.uuid) {
                    if (cartProduct.quantityProduct > product.total!!) {
                        throw FlowException(
                            """O produto de id ${product.uuid} nao pode ser vendido pois a quantidade
                             requirida ultrapassa quantidade de estoque""", "ERROR"
                        )
                    }
                }
            }
        }

        var totalValueOrder = BigDecimal.ZERO
        cartUserProducts?.itens?.forEach {
            totalValueOrder += it.saleValue ?: BigDecimal.ZERO
        }

        val nameCompany = integracaoSweetStock.getNameCompany(
            cartUserProducts?.itens?.firstOrNull()?.uuid ?: ""
        ).body

        val order = Order(
            idOrder = null,
            statusOrder = "Pronto para preparação",
            uuidUser = uuidUser,
            valueOrder = totalValueOrder,
            quantityItems = cartUserProducts?.itens?.size,
            dateOrder = LocalDateTime.now(),
            nameConfectionery = nameCompany,
            card = payment.cardNumber,
            brandCard = payment.brand
        )

        cartUserProducts?.itens?.forEach { product ->
            cartUserProductsOriginal.forEach { cartProduct ->
                if (cartProduct.uuidProduct == product.uuid) {
                    integracaoSweetStock.sellProduct(
                        uuid = product.uuid,
                        soldQuantity = cartProduct.quantityProduct
                    )
                }
            }
        }

        cartService.eraseUserCart(uuidUser)

        val createdOrder = orderRepository.save(order)

        if (createdOrder.card?.replace("\\s".toRegex(), "")?.length == 16) {
            createdOrder.card = "**** **** **** " + createdOrder.card!!.takeLast(4)
        }

        cartUserProducts?.itens?.forEach { product ->
            cartUserProductsOriginal.forEach { cartProduct ->
                if (cartProduct.uuidProduct == product.uuid) {
                    orderItemRepository.save(
                        OrderItem(
                            null,
                            order.idOrder,
                            product.name,
                            product.saleValue,
                            product.expirationDate,
                            product.isRefigerated,
                            cartProduct.quantityProduct,
                            product.unitMeasurement,
                            product.category?.name
                        )
                    )
                }
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
            OrderResponse(
                idOrder = createdOrder.idOrder,
                statusOrder = createdOrder.statusOrder,
                dateOrder = createdOrder.dateOrder,
                nameConfectionery = createdOrder.nameConfectionery,
                valueOrder = createdOrder.valueOrder,
                card = createdOrder.card,
                brandCard = createdOrder.brandCard,
                quantityItems = createdOrder.quantityItems,
            )
        )
    }

    fun getUserOrders(uuidUser: String): ResponseEntity<List<OrderResponse>> {
        if (userRepository.countByUuid(uuidUser) != 1L) {
            return ResponseEntity.badRequest().build()
        }

        val orderResponses = orderRepository.getUserOrders(uuidUser).map {
            OrderResponse(
                idOrder = it.idOrder,
                statusOrder = it.statusOrder,
                dateOrder = it.dateOrder,
                nameConfectionery = it.nameConfectionery,
                valueOrder = it.valueOrder,
                card = it.card,
                brandCard = it.brandCard,
                quantityItems = it.quantityItems
            )
        }

        if (orderResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }

        return ResponseEntity.ok().body(orderResponses)
    }

    fun getUsersOrdersByCompany(nameCompany: String): ResponseEntity<List<OrderResponse>> {
        if (orderRepository.countByNameCompany(nameCompany) == 0L) {
            return ResponseEntity.badRequest().build()
        }

        val orderResponses = orderRepository.getCompanyOrders(nameCompany).map {
            OrderResponse(
                idOrder = it.idOrder,
                statusOrder = it.statusOrder,
                dateOrder = it.dateOrder,
                nameConfectionery = it.nameConfectionery,
                valueOrder = it.valueOrder,
                card = it.card,
                brandCard = it.brandCard,
                quantityItems = it.quantityItems
            )
        }

        if (orderResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }

        return ResponseEntity.ok().body(orderResponses)
    }

    fun updateStatus(idOrder: Int, newStatus: String): ResponseEntity<Any> {
        if (orderRepository.countById(idOrder) != 1L) {
            return ResponseEntity.badRequest().build()
        }

        orderRepository.updateStatus(
            statusOrder = newStatus,
            idOrder = idOrder
        )

        return ResponseEntity.ok().build()
    }

}
