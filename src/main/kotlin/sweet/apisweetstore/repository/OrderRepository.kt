package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import sweet.apisweetstore.model.Order

@Repository
interface OrderRepository: JpaRepository<Order, Int> {


    @Query("select o from Order o where o.uuidUser = ?1")
    fun getUserOrders(uuidUser: String): List<Order>

    @Query("select count(o) from Order o where o.idOrder = ?1")
    fun countById(idOrder: Int): Long

    @Transactional
    @Modifying
    @Query("update Order o set o.statusOrder = ?1 where o.idOrder = ?2")
    fun updateStatus(statusOrder: String, idOrder: Int)

    @Query("select count(o) from Order o where o.nameConfectionery = ?1")
    fun countByNameCompany(nameCompany: String): Long

    @Query("select o from Order o where o.nameConfectionery = ?1")
    fun getCompanyOrders(nameCompany: String): List<Order>


}
