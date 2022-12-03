package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sweet.apisweetstore.model.OrderItem
import sweet.apisweetstore.service.OrderItemService

@RestController
@RequestMapping("/order-item")
@CrossOrigin(origins = arrayOf("*"))
class OrderItemController(
    private val orderItemService: OrderItemService
) {

    @GetMapping("/get-order-itens/{idOrder}")
    fun getOrderItems(@PathVariable idOrder: Int): ResponseEntity<List<OrderItem>>{
        return orderItemService.getOrderItems(idOrder)
    }
}