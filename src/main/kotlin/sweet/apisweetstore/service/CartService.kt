package sweet.apisweetstore.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.ItemCartRequest
import sweet.apisweetstore.dto.response.AddCartResponse
import sweet.apisweetstore.dto.response.CartResponse
import sweet.apisweetstore.enums.CartMessage
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
        val userExist = userRepository.countByUuid(uuidUser)

        if (userExist != 1L) {
            return ResponseEntity.status(404).body(AddCartResponse(message = CartMessage.USER_NOT_EXIST.message))
        }

        if (products.isEmpty()) {
            return ResponseEntity.status(400).body(
                AddCartResponse(
                    message = CartMessage.EMPTY_LIST_CARD.message
                )
            )
        }

        val validQuantitys = products.any { it.quantityProduct <= 0 }

        if (validQuantitys) {
            return ResponseEntity.status(400).body(AddCartResponse(message = CartMessage.SHOULD_BIG_THAN_0.message))
        }

        val validDiffCompanies = products.distinctBy { it.uuidCompany }

        if (validDiffCompanies.size > 1) {
            return ResponseEntity.status(400)
                .body(AddCartResponse(message = CartMessage.CAN_SEND_JUST_ONE_COMPANY.message))
        }

        val validDiffProducts = products.distinctBy { it.uuidProduct }

        if (validDiffProducts.size != products.size) {
            return ResponseEntity.status(400).body(
                AddCartResponse(message = CartMessage.SEND_JUST_DIFFERENT_PRODUCTS.message)
            )
        }

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
        if (userRepository.countByUuid(uuidUser) != 1L) return ResponseEntity.status(404).body(
            CartResponse(message = CartMessage.USER_NOT_EXIST.message)
        )

        val itensCart = cartRepository.getUserCartByUuid(uuidUser)

        if (itensCart.isEmpty()) {
            return ResponseEntity.status(404).body(
                CartResponse(
                    message = CartMessage.EMPTY_CART.message
                )
            )
        }

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
        if (userRepository.countByUuid(uuidUser) != 1L) return ResponseEntity.status(404).body(
            CartResponse(
                message = CartMessage.USER_NOT_EXIST.message
            )
        )

        val cartUserItems = cartRepository.getUserCartByUuid(uuidUser)

        if (cartUserItems.isEmpty()) {
            return ResponseEntity.status(400).body(
                CartResponse(
                    message = CartMessage.EMPTY_CART_NOTHING_TO_ERASE.message
                )
            )
        }

        var erased = cartRepository.eraseCartByUuidUser(uuidUser)

        return ResponseEntity.status(200).body(
            CartResponse(
                message = CartMessage.ERASE_SUCCESS.message + " $erased itens excluidos do carrinho"
            )
        )
    }

    fun deleteItemFromCartUser(uuidUser: String, uuidProduct: String): ResponseEntity<CartResponse> {
        if (userRepository.countByUuid(uuidUser) != 1L) return ResponseEntity.status(404).body(
            CartResponse(
                message = CartMessage.USER_NOT_EXIST.message
            )
        )

        if (cartRepository.countItemFromCartUser(uuidUser, uuidProduct) == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                CartResponse(
                    message = CartMessage.NOT_FOUND_ITEM_ERROR.message
                )
            )
        }

        cartRepository.deleteItemFromCartUser(uuidUser, uuidProduct)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    fun updateQuantityItem(uuidUser: String, uuidProduct: String, newQuantity: Int): ResponseEntity<CartResponse> {
        if (userRepository.countByUuid(uuidUser) != 1L) return ResponseEntity.status(404).body(
            CartResponse(
                message = CartMessage.USER_NOT_EXIST.message
            )
        )

        if (cartRepository.countItemFromCartUser(uuidUser, uuidProduct) == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                CartResponse(
                    message = CartMessage.NOT_FOUND_ITEM_ERROR.message
                )
            )
        }

        if (newQuantity <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CartResponse(
                    message = CartMessage.UPDATE_ERROR_ITEM.message
                )
            )
        }

        val quantityUpdate = cartRepository.updateQuantityItem(uuidUser, uuidProduct, newQuantity)

        if (quantityUpdate == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CartResponse(
                    message = CartMessage.UPDATE_ERROR_ITEM_NO_DIFF.message
                )
            )
        }

        return ResponseEntity.ok().build()
    }
}
