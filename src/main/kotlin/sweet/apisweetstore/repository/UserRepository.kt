package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import sweet.apisweetstore.model.Cart
import sweet.apisweetstore.model.Product
import sweet.apisweetstore.model.User

interface UserRepository: JpaRepository<User, String> {
    @Query("select count(u) from User u where u.uuid = ?1")
    fun countByUuid(uuid: String): Long

    @Query("select count(u) from User u where u.email = ?1")
    fun countByEmail(email: String): Long

    @Query("select count(u) from User u where u.email = ?1 and u.password = ?2")
    fun auth(email: String, password: String): Long

    @Query("select u.uuid from User u where u.email = ?1 and u.password = ?2")
    fun findUuidUser(email: String, password: String): String?
    @Query("select count(u) from User u where u.uuid = ?1 and u.password = ?2")
    fun verifyActualPassword(uuid: String, password: String): Long

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?1 where u.uuid = ?2")
    fun changePassword(password: String, uuid: String): Int

    fun findByUuid(uuid: String): User?

}