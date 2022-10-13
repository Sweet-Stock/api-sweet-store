package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sweet.apisweetstore.model.Cart

@Repository
interface CartRepository: JpaRepository<Cart, String> {

}
