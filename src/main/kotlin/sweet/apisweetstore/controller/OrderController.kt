package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sweet.apisweetstore.dto.request.OrderRequest
import sweet.apisweetstore.dto.response.OrderResponse
import sweet.apisweetstore.service.OrderService

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = arrayOf("*"))
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    fun createOrder(@RequestBody orderRequest: OrderRequest): ResponseEntity<OrderResponse>{
        return orderService.createOrder(orderRequest)
    }

    @GetMapping("/get-user-orders/{uuidUser}")
    fun getUserOrders(@PathVariable uuidUser: String): ResponseEntity<List<OrderResponse>>{
        return orderService.getUserOrders(uuidUser)
    }

    @GetMapping("/get-users-orders-by-company/{nameCompany}")
    fun getUsersOrdersByCompany(@PathVariable nameCompany: String): ResponseEntity<List<OrderResponse>>{
        return  orderService.getUsersOrdersByCompany(nameCompany)
    }

    @PatchMapping("{idOrder}/{newStatus}")
    fun updateStatus(@PathVariable idOrder: Int, @PathVariable newStatus: String): ResponseEntity<Any>{
        return orderService.updateStatus(idOrder, newStatus)
    }
}