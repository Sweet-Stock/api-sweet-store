package sweet.apisweetstore.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.OrderRequest
import sweet.apisweetstore.dto.response.AddressResponse
import sweet.apisweetstore.dto.response.OrderResponse
import sweet.apisweetstore.enums.CartMessage
import sweet.apisweetstore.exception.FlowException
import sweet.apisweetstore.integration.IntegracaoSweetStock
import sweet.apisweetstore.model.Order
import sweet.apisweetstore.model.OrderItem
import sweet.apisweetstore.repository.*
import java.math.BigDecimal

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

        uuidUser.verifyUserByUuid()

        if (paymentRepository.countById(idPayment) != 1L)
            throw FlowException("Metodo de pagamento não existe", "ERROR", 404)

        val payment = paymentRepository.findById(idPayment).get()

        if (payment.uuidUser != uuidUser)
            throw FlowException(
                "Metodo de pagamento não permitido para este usuário",
                "ERROR",
                HttpStatus.UNAUTHORIZED.value()
            )

        val cartUserProducts = cartService.getUserCartByUuid(uuidUser).body
        val cartUserProductsOriginal = cartRepository.getUserCartByUuid(uuidUser)

        if (cartUserProducts?.itens?.size == 0)
            throw FlowException("Ocorreu um erro ao mapear produtos do carrinho","ERROR")

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
            uuidUser = uuidUser,
            valueOrder = totalValueOrder,
            quantityItems = cartUserProducts?.itens?.size,
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
        if (userRepository.countByUuid(uuidUser) != 1L) return ResponseEntity.badRequest().build()

        val orderResponses = orderRepository.getUserOrders(uuidUser)
            .map { (idOrder, _, statusOrder, valueOrder, quantityItems, dateOrder, nameConfectionery, card, brandCard) ->
                OrderResponse(
                    idOrder = idOrder,
                    statusOrder = statusOrder,
                    dateOrder = dateOrder,
                    nameConfectionery = nameConfectionery,
                    valueOrder = valueOrder,
                    card = card,
                    brandCard = brandCard,
                    quantityItems = quantityItems
                )
            }

        if (orderResponses.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.ok().body(orderResponses)
    }

    fun getUsersOrdersByCompany(nameCompany: String): ResponseEntity<List<OrderResponse>> {
        println(nameCompany)

        if (orderRepository.countByNameCompany(nameCompany) == 0L) return ResponseEntity.noContent().build()

        val orderResponses = orderRepository.getCompanyOrders(nameCompany)
            .map { (idOrder, uuidUser, statusOrder, valueOrder, quantityItems, dateOrder, nameConfectionery, card, brandCard) ->
                var user = userRepository.findByUuid(uuidUser?: "")
                OrderResponse(
                    idOrder = idOrder,
                    statusOrder = statusOrder,
                    dateOrder = dateOrder,
                    nameConfectionery = nameConfectionery,
                    valueOrder = valueOrder,
                    card = card,
                    brandCard = brandCard,
                    quantityItems = quantityItems,
                    nameUser = user.name,
                    address = AddressResponse(
                        city = user.address?.city,
                        complement = user.address?.complement,
                        neighborhood = user.address?.neighborhood,
                        number = user.address?.number,
                        state = user.address?.state,
                        street = user.address?.street,
                        cep = user.address?.cep
                    )
                )
            }

        if (orderResponses.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.ok().body(orderResponses)
    }

    fun updateStatus(idOrder: Int, newStatus: String): ResponseEntity<Any> {
        if (orderRepository.countById(idOrder) != 1L) return ResponseEntity.badRequest().build()

        orderRepository.updateStatus(statusOrder = newStatus, idOrder = idOrder)

        return ResponseEntity.ok().build()
    }

    fun String.verifyUserByUuid() {
        if (userRepository.countByUuid(this) != 1L)
            throw FlowException(message = CartMessage.USER_NOT_EXIST.message, statusCode = 404)
    }
}
