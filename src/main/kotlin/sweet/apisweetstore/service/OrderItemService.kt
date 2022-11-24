package sweet.apisweetstore.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.model.OrderItem
import sweet.apisweetstore.repository.OrderItemRepository

@Service
class OrderItemService(
    private val orderItemRepository: OrderItemRepository
) {

    fun getOrderItems(idOrder: Int): ResponseEntity<List<OrderItem>> {
        return ResponseEntity.ok(orderItemRepository.findByIdOrder(idOrder))
    }


}
