package sweet.apisweetstore.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_order")
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idOrder: Int? = null,
    val uuidUser: String? = "",

    val statusOrder: String? = "Pronto para preparação",
    val valueOrder: BigDecimal? = BigDecimal.ZERO,
    val quantityItems: Int? = 0,
    val dateOrder: LocalDateTime? = LocalDateTime.now(),

    val nameConfectionery: String? = "",

    var card: String? = "",
    val brandCard: String? = "",
)