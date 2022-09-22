package sweet.apisweetstore.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "tb_rating")
data class Rating(
        @Id
        val uuid: String? = UUID.randomUUID().toString(),
        val starsNumber: Double,
        val title: String,
        val comment: String,
        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
        val user: User,
        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn(name = "product_uuid", referencedColumnName = "uuid")
        val product: Product,
        val dateCreate: LocalDateTime? = LocalDateTime.now(),
        val dateUpdate: LocalDateTime? = LocalDateTime.now(),

        )
