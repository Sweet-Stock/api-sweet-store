package sweet.apisweetstore.dto.request

import sweet.apisweetstore.model.Address

data class StoreRequest(
    val name: String,
    val cnpj: String,
    val email: String,
    val phone: String,
    val image: String? = "",
    val address: Address,
)
