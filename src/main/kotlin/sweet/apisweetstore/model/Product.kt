package sweet.apisweetstore.model

import sweet.apisweetstore.enums.Category
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table


@Entity
@Table(name = "tb_product")
data class Product(
    @Id
    val uuid: String? = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = "",
    val price: Double,
    val image: String,
    val category: String,
    @OneToOne
    val nutritionalValue: NutritionalFacts,
    @ManyToOne
    val store: Store
)
