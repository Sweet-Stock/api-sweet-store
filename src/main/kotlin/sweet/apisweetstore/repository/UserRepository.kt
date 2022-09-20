package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import sweet.apisweetstore.model.User

interface UserRepository: JpaRepository<User, String> {
    @Query("select count(u) from User u where u.uuid = ?1")
    fun countByUuid(uuid: String): Long

    @Query("select count(u) from User u where u.email = ?1")
    fun countByEmail(email: String): Long

    @Query("select count(u) from User u where u.email = ?1 and u.password = ?2")
    fun auth(email: String, password: String): Long

    @Query("select u.uuid from User u where u.email = ?1 and u.password = ?2")
    fun findUuidUser(email: String, password: String): String


}