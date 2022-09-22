package sweet.apisweetstore.model

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table


@Entity
@Table(name = "tb_product")
data class Product(
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
