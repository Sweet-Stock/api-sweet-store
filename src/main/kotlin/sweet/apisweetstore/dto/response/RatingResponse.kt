package sweet.apisweetstore.dto.response

import sweet.apisweetstore.model.Product
import sweet.apisweetstore.model.User


data class RatingResponse(
    val starsNumber: Double,
    val title: String,
    val comment: String,
    val user: User,
    val product: Product,
)
