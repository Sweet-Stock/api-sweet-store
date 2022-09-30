package sweet.apisweetstore.dto.request

import sweet.apisweetstore.model.Address
import java.util.*

data class AddressRequest(
    val city: String,
    val complement: String? = "",
    val neighborhood: String? = "",
    val number: Int,
    val state: String,
    val street: String,
    val cep: String
)