package sweet.apisweetstore.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.ItemCartRequest
import sweet.apisweetstore.dto.response.AddCartResponse
import sweet.apisweetstore.dto.response.CartResponse
import sweet.apisweetstore.enums.CartMessage
import sweet.apisweetstore.exception.FlowException
import sweet.apisweetstore.integration.IntegracaoSweetStock
import sweet.apisweetstore.integration.ProductResponseAPI
import sweet.apisweetstore.model.Cart
import sweet.apisweetstore.repository.CartRepository
import sweet.apisweetstore.repository.UserRepository
import java.time.LocalDateTime

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val integracaoSweetStock: IntegracaoSweetStock
) {

    fun addProductToCart(products: List<ItemCartRequest>, uuidUser: String): ResponseEntity<AddCartResponse> {
        uuidUser.verifyUserByUuid()

        if (products.isEmpty())
            throw FlowException(message = CartMessage.EMPTY_LIST_CARD.message)

        if (products.any { it.quantityProduct <= 0 })
            throw FlowException(message = CartMessage.SHOULD_BIG_THAN_0.message)

        val validDiffCompanies = products.distinctBy { it.uuidCompany }.size

        if (validDiffCompanies > 1)
            throw FlowException(message = CartMessage.CAN_SEND_JUST_ONE_COMPANY.message)

        if (products.distinctBy { it.uuidProduct }.size != products.size)
            throw FlowException(message = CartMessage.SEND_JUST_DIFFERENT_PRODUCTS.message)

        var saved = 0

        products.forEach {

            val count = cartRepository.validDiffCompaniesAndProductForUser(
                uuidUser,
                it.uuidProduct,
                it.uuidCompany
            )

            if (count == 0L) {
                saved++
                cartRepository.save(
                    Cart(
                        idItem = null,
                        uuidUser = uuidUser,
                        uuidProduct = it.uuidProduct,
                        uuidCompany = it.uuidCompany,
                        quantityProduct = it.quantityProduct,
                        dateAddition = LocalDateTime.now()
                    )
                )
            }
        }

        if (saved != products.size) {
            return ResponseEntity.status(400).body(
                AddCartResponse(
                    message = CartMessage.ERROR_SOMEONE_ITEM.message,
                    quantityRegistered = saved
                )
            )
        }

        return ResponseEntity.ok(
            AddCartResponse(
                message = CartMessage.ALL_ITENS_SAVED.message,
                quantityRegistered = saved
            )
        )
    }

    fun getUserCartByUuid(uuidUser: String): ResponseEntity<CartResponse> {
        uuidUser.verifyUserByUuid()

        val itensCart = cartRepository.getUserCartByUuid(uuidUser)

        if (itensCart.isEmpty())
            throw FlowException(message = CartMessage.EMPTY_CART.message, statusCode = 404)

        var uuidsRequest = mutableListOf<String>()

        itensCart.forEach {
            uuidsRequest.add(it.uuidProduct)
        }

        val listProducts = integracaoSweetStock.getProductsByUuid(
            uuidsRequest
        ).body

        return ResponseEntity.ok().body(
            CartResponse(
                listProducts?.map {
                    ProductResponseAPI(
                        uuid = it.uuid,
                        name = it.name,
                        saleValue = it.saleValue,
                        expirationDate = it.expirationDate,
                        dateInsert = it.dateInsert,
                        dateUpdate = it.dateUpdate,
                        isRefigerated = it.isRefigerated,
                        sold = it.sold,
                        total = it.total,
                        unitMeasurement = it.unitMeasurement,
                        category = it.category,
                        picture = it.picture,
                        nutritionalFacts = it.nutritionalFacts
                    )
                }
            )
        )
    }

    fun eraseUserCart(uuidUser: String): ResponseEntity<CartResponse> {
        uuidUser.verifyUserByUuid()

        if (cartRepository.getUserCartByUuid(uuidUser).isEmpty())
            throw FlowException(message = CartMessage.EMPTY_CART_NOTHING_TO_ERASE.message, statusCode = 404)

        return ResponseEntity.status(200).body(
            CartResponse(
                message = CartMessage.ERASE_SUCCESS.message + """ ${cartRepository.eraseCartByUuidUser(uuidUser)}
                    itens excluidos do carrinho""".trimIndent()
            )
        )
    }

    fun deleteItemFromCartUser(uuidUser: String, uuidProduct: String): ResponseEntity<CartResponse> {
        uuidUser.verifyUserByUuid()

        if (cartRepository.countItemFromCartUser(uuidUser, uuidProduct) == 0L)
            throw FlowException(message = CartMessage.NOT_FOUND_ITEM_ERROR.message, statusCode = 404)

        cartRepository.deleteItemFromCartUser(uuidUser, uuidProduct)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    fun updateQuantityItem(uuidUser: String, uuidProduct: String, newQuantity: Int): ResponseEntity<CartResponse> {
        uuidUser.verifyUserByUuid()

        if (cartRepository.countItemFromCartUser(uuidUser, uuidProduct) == 0L)
            throw FlowException(message = CartMessage.NOT_FOUND_ITEM_ERROR.message, statusCode = 404)

        if (newQuantity <= 0)
            throw FlowException(message = CartMessage.UPDATE_ERROR_ITEM.message)

        if (cartRepository.updateQuantityItem(uuidUser, uuidProduct, newQuantity) == 0)
            throw FlowException(message = CartMessage.UPDATE_ERROR_ITEM_NO_DIFF.message)

        return ResponseEntity.ok().build()
    }

    fun String.verifyUserByUuid() {
        if (userRepository.countByUuid(this) != 1L)
            throw FlowException(message = CartMessage.USER_NOT_EXIST.message, statusCode = 404)
    }


}
