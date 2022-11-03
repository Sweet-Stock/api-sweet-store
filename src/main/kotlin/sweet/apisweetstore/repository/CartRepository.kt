package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sweet.apisweetstore.model.Cart

@Repository
interface CartRepository : JpaRepository<Cart, String> {
    @Query("select count(c) from Cart c where c.uuidUser = ?1 and (c.uuidProduct = ?2 or c.uuidCompany <> ?3)")
    fun validDiffCompaniesAndProductForUser(uuidUser: String, uuidProduct: String, uuidCompany: String): Long


    @Query("select c from Cart c where c.uuidUser = ?1")
    fun getUserCartByUuid(uuidUser: String): List<Cart>

}
