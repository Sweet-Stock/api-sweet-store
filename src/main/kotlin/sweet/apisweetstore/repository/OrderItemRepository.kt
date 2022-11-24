package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sweet.apisweetstore.model.OrderItem

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Int> {

    @Query("select o from OrderItem o where o.idOrder = ?1")
    fun findByIdOrder(idOrder: Int): List<OrderItem>
}