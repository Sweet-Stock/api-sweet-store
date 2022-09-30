package sweet.apisweetstore.dto.request

import sweet.apisweetstore.model.Product
import sweet.apisweetstore.model.User

data class RatingRequest(
    val starsNumber: Double,
    val title: String,
    val comment: String,
    val user: User,
    val product: Product,
)
