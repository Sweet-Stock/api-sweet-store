package sweet.apisweetstore.service

import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import sweet.apisweetstore.repository.CartRepository

@Service
class CartService(
    private val cartRepository: CartRepository
) {

    fun addProductToCart() {
//        cartRepository.addProductToCart()
    }
}
