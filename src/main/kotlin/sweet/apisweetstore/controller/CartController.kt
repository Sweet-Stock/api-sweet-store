package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sweet.apisweetstore.dto.request.ItemCartRequest
import sweet.apisweetstore.dto.response.AddCartResponse
import sweet.apisweetstore.dto.response.CartResponse
import sweet.apisweetstore.service.CartService

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = arrayOf("*"))
class CartController(
    private val cartService: CartService,
) {

    @PostMapping("/add/{uuidUser}")
    fun addProductToCart(
        @PathVariable uuidUser: String,
        @RequestBody productsUuids: List<ItemCartRequest>
    ): ResponseEntity<AddCartResponse> {
        return cartService.addProductToCart(productsUuids, uuidUser)
    }

    @GetMapping("/get-user-cart-by-uuid/{uuidUser}")
    fun getUserCartByUuid(@PathVariable uuidUser: String): ResponseEntity<CartResponse> {
        return cartService.getUserCartByUuid(uuidUser)
    }

    @DeleteMapping("/erase-user-cart/{uuidUser}")
    fun eraseUserCart(@PathVariable uuidUser: String): ResponseEntity<CartResponse> {
        return cartService.eraseUserCart(uuidUser)
    }

    @DeleteMapping("/delete-item-from-cart-user/{uuidUser}/{uuidProduct}")
    fun deleteItemFromCartUser(
        @PathVariable uuidUser: String,
        @PathVariable uuidProduct: String
    ): ResponseEntity<CartResponse> {
        return cartService.deleteItemFromCartUser(uuidUser, uuidProduct)
    }

    @PatchMapping("/update-quantity-item/{uuidUser}/{uuidProduct}/{newQuantity}")
    fun updateQuantityItem(
        @PathVariable uuidUser: String,
        @PathVariable uuidProduct: String,
        @PathVariable newQuantity: Int
    ): ResponseEntity<*>? {
        return cartService.updateQuantityItem(uuidUser, uuidProduct, newQuantity)
    }

}