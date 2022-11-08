package sweet.apisweetstore.dto.response

data class CartResponse(
    val itens: List<ItemResponse>? = mutableListOf(),
    val message: String ? = ""
)