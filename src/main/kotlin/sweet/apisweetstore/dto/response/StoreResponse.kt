package sweet.apisweetstore.dto.response

import sweet.apisweetstore.model.Address

data class StoreResponse(
    val name: String,
    val cnpj: String,
    val email: String,
    val phone: String,
    val image: String? = "",
    val address: Address,
)
