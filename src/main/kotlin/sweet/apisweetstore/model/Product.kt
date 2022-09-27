package sweet.apisweetstore.model

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "tb_product")
data class Product(
        @Id
        val uuid: String? = UUID.randomUUID().toString(),
        val name: String? = "",
        val description: String? = "",
        val price: Double,
        val image: String? = "",
        @ManyToOne
        val category: String? = "",
        @OneToOne
        val nutritionalValue: String? = "",
        @ManyToOne
        val store: String? = "",
)
