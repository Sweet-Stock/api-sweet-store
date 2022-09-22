package sweet.apisweetstore.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "tb_subscription")
data class Subscription(
        @Id
        val uuid: String? = UUID.randomUUID().toString(),
        val type: String? = "",
        val nextDeliveryDate: String? = "",
        val value: Double? = null,
        @ManyToOne
        val payment: String? = "",
        @OneToOne
        val user: String? = "",
        val dateCreate: LocalDateTime? = LocalDateTime.now(),
        val dateUpdate: LocalDateTime? = LocalDateTime.now(),


        )
