package sweet.apisweetstore.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_order_item")
data class OrderItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val idOrder: Int? = null,
    val name: String? = "",
    val saleValue: BigDecimal? = BigDecimal.ZERO,
    val expirationDate: Date,
    val isRefigerated: Boolean? = null,
    val total: Int? = 0,
    val unitMeasurement: String? = "",
    val category: String? = "",
)