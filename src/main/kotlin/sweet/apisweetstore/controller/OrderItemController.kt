package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sweet.apisweetstore.model.OrderItem
import sweet.apisweetstore.service.OrderItemService

@RestController
@RequestMapping("/order-item")
class OrderItemController(
    private val orderItemService: OrderItemService
) {

    @GetMapping("/get-order-itens/{idOrder}")
    fun getOrderItems(@PathVariable idOrder: Int): ResponseEntity<List<OrderItem>>{
        return orderItemService.getOrderItems(idOrder)
    }
}