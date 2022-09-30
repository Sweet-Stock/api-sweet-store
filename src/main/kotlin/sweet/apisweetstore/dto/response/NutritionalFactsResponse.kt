package sweet.apisweetstore.dto.response

data class NutritionalFactsResponse(
    val calories: Int? = 0,
    val sodium: Double? = 0.0,
    val sugar: Double? = 0.0,
    val fat: Double? = 0.0,
    val weight: Double? = 0.0,
    val gluten: Boolean? = true,
)
