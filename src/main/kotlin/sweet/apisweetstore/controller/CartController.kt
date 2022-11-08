package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sweet.apisweetstore.dto.request.ItemCartRequest
import sweet.apisweetstore.dto.response.AddCartResponse
import sweet.apisweetstore.dto.response.CartResponse
import sweet.apisweetstore.dto.response.ItemResponse
import sweet.apisweetstore.service.CartService

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartService: CartService,
) {

    @PostMapping("/add/{uuidUser}")
    fun addProductToCart(
        @RequestParam uuidUser: String,
        @RequestBody productsUuids: List<ItemCartRequest>
    ): ResponseEntity<AddCartResponse> {
        return cartService.addProductToCart(productsUuids, uuidUser)
    }

    @GetMapping("/get-user-cart-by-uuid/{uuidUser}")
    fun getUserCartByUuid(@RequestParam uuidUser: String): ResponseEntity<CartResponse> {
        return cartService.getUserCartByUuid(uuidUser)
    }

    @DeleteMapping("/erase-user-cart/{uuidUser}")
    fun eraseUserCart(@RequestParam uuidUser: String): ResponseEntity<CartResponse> {
        return cartService.eraseUserCart(uuidUser)
    }

    @DeleteMapping("/delete-item-from-cart-user/{uuidUser}/{uuidProduct}")
    fun deleteItemFromCartUser(
        @RequestParam uuidUser: String,
        @RequestParam uuidProduct: String
    ): ResponseEntity<CartResponse> {
        return cartService.deleteItemFromCartUser(uuidUser, uuidProduct)
    }

    @PatchMapping("/update-quantity-item/{uuidUser}/{uuidProduct}/{newQuantity}")
    fun updateQuantityItem(
        @RequestParam uuidUser: String,
        @RequestParam uuidProduct: String,
        @RequestParam newQuantity: Int
    ): ResponseEntity<CartResponse> {
        return cartService.updateQuantityItem(uuidUser, uuidProduct, newQuantity)
    }

}