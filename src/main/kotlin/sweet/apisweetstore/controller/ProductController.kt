package sweet.apisweetstore.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sweet.apisweetstore.service.ProductService

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping("/sell-products")
    fun sellProducts(){

    }
}