package sweet.apisweetstore.dto.response

data class AddCartResponse(
    val message: String,
    val quantityRegistered: Int ? = 0
)
