package sweet.apisweetstore.model

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tb_cart")
data class Cart(

    @Id
    val uuid: String? = UUID.randomUUID().toString(),

    @OneToMany
    val products: List<Product>? = mutableListOf<Product>()
)