package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import sweet.apisweetstore.model.Payment

interface PaymentRepository: JpaRepository<Payment, Int> {

    fun findAllByUuidUser(uuidUser: String): List<Payment>

    @Query("select count(p) from Payment p where p.idPayment = ?1")
    fun countById(id: Int): Long
}