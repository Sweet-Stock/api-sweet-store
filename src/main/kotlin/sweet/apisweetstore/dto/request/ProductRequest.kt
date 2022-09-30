package sweet.apisweetstore.dto.request

data class ProductRequest(
    val name: String,
    val description: String? = "",
    val price: Double,
    val image: String,
    val category: String,
    val nutritionalValue: String? = "",
    val store: String? = "",
)
