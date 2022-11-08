package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import sweet.apisweetstore.model.Cart

@Repository
interface CartRepository : JpaRepository<Cart, String> {
    @Query("select count(c) from Cart c where c.uuidUser = ?1 and (c.uuidProduct = ?2 or c.uuidCompany <> ?3)")
    fun validDiffCompaniesAndProductForUser(uuidUser: String, uuidProduct: String, uuidCompany: String): Long


    @Query("select c from Cart c where c.uuidUser = ?1")
    fun getUserCartByUuid(uuidUser: String): List<Cart>

    @Transactional
    @Modifying
    @Query("delete from Cart c where c.uuidUser = ?1")
    fun eraseCartByUuidUser(uuidUser: String): Int

    @Query("select count(c) from Cart c where c.uuidUser = ?1 and c.uuidProduct = ?2")
    fun countItemFromCartUser(uuidUser: String, uuidProduct: String): Long

    @Transactional
    @Modifying
    @Query("delete from Cart c where c.uuidUser = ?1 and c.uuidProduct = ?2")
    fun deleteItemFromCartUser(uuidUser: String, uuidProduct: String): Int


    @Transactional
    @Modifying
    @Query("update Cart c set c.quantityProduct = ?3 where c.uuidUser = ?1 and c.uuidProduct = ?2 and c.quantityProduct <> ?3")
    fun updateQuantityItem(uuidUser: String, uuidProduct: String, quantityProduct: Int): Int

}
