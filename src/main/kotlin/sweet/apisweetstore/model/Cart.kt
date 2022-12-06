package sweet.apisweetstore.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_cart")
data class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idItem: Int? = null,
    val uuidUser: String,
    val uuidProduct: String,
    val uuidCompany: String,
    val quantityProduct: Int
)