package sweet.apisweetstore.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "tb_nutritionalFacts")
data class NutritionalFacts(
        @Id
        val uuid: String? = UUID.randomUUID().toString(),
        val calories: Int? = 0,
        val sodium: Double? = 0.0,
        val sugar: Double? = 0.0,
        val fat: Double? = 0.0,
        val weight: Double? = 0.0,
        val gluten: Boolean? = true,
        val dateCreate: LocalDateTime? = LocalDateTime.now(),
        val dateUpdate: LocalDateTime? = LocalDateTime.now(),

        )
