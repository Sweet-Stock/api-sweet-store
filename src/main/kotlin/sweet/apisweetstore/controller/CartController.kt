package sweet.apisweetstore.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sweet.apisweetstore.service.CartService

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartService: CartService
) {

    @PostMapping("/add")
    fun addProductToCart(){
        cartService.addProductToCart()
    }
}