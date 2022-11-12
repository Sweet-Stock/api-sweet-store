package sweet.apisweetstore.dto.response

import sweet.apisweetstore.integration.ProductResponseAPI

data class CartResponse(
    val itens: List<ProductResponseAPI>? = mutableListOf(),
    val message: String ? = ""
)