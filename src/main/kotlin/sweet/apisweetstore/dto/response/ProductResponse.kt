package sweet.apisweetstore.dto.response

import sweet.apisweetstore.integration.NutritionalFactsResponseAPI
import sweet.apisweetstore.model.NutritionalFacts
import sweet.apisweetstore.model.Store

data class ProductResponse(
    val name: String,
    val description: String? = "",
    val price: Double,
    val image: String,
    val category: String,
    val nutritionalValue: NutritionalFacts,
    val store: Store,
)
