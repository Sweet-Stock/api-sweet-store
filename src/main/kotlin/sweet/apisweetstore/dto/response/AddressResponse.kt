package sweet.apisweetstore.dto.response

data class AddressResponse(
    val city: String? = "",
    val complement: String? = "",
    val neighborhood: String? = "",
    val number: String? = "",
    val state: String? = "",
    val street: String? = "",
    val cep: String? = ""
)
