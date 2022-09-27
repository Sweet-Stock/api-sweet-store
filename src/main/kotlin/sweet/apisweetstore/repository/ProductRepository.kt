package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import sweet.apisweetstore.model.Product

interface ProductRepository: JpaRepository<Product, String> {

    fun findAllByCategory(category:String): String

    fun countAllByCategory(category:String):Long

    fun deleteByUuid(uuid: String)

    fun findByUuid(uuid: String): String

    fun findAllByPrice(price: Double): String

}