package sweet.apisweetstore.dto.request

data class OrderRequest(
    val uuidUser: String,
    val idPayment: Int,
)