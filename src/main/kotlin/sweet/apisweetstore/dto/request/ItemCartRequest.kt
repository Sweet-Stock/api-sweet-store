package sweet.apisweetstore.dto.request

data class ItemCartRequest(
    val uuidProduct: String,
    val uuidCompany: String,
    val quantityProduct: Int,
)
