package sweet.apisweetstore.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.ItemCartRequest
import sweet.apisweetstore.dto.response.AddCartResponse
import sweet.apisweetstore.dto.response.CartResponse
import sweet.apisweetstore.enums.CartMessage
import sweet.apisweetstore.model.Cart
import sweet.apisweetstore.repository.CartRepository
import sweet.apisweetstore.repository.UserRepository
import java.time.LocalDateTime

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
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

    fun getUserCartByUuid(uuidUser: String): ResponseEntity<List<CartResponse>> {
        val userExist = userRepository.countByUuid(uuidUser)

        if (userExist != 1L) {
            return ResponseEntity.status(404).body(
                mutableListOf(
                    CartResponse(message = CartMessage.USER_NOT_EXIST.message)
                )
            )
        }

        val itensCart = cartRepository.getUserCartByUuid(uuidUser)

        if (itensCart.isEmpty()) {
            return ResponseEntity.status(400).body(
                mutableListOf(
                    CartResponse(message = CartMessage.EMPTY_CART.message)
                )
            )
        }

        return ResponseEntity.ok().body(
            itensCart.map {
                CartResponse(
                    idItem = it.idItem,
                    uuidProduct = it.uuidProduct,
                    uuidCompany = it.uuidCompany,
                    quantityProduct = it.quantityProduct,
                    dateAddition = it.dateAddition
                )
            }
        )
    }
}
