package sweet.apisweetstore.model

import javax.persistence.*


@Entity
@Table(name = "tb_nutritional_facts")
data class NutritionalFacts(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val calories: Int? = 0,
    val sodium: Double? = 0.0,
    val sugars: Double? = 0.0,
    val protein: Double = 0.0,
    val fat: Double? = 0.0,
    val weight: Double? = 0.0,
    val gluten: Boolean? = true,
)