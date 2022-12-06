package sweet.apisweetstore.dto.response

data class LoginResponse(
    val uuid: String? = null,
    val name: String? = "",
    val profile: String? = "",
    val email: String,
    val message: String,
    val addressResponse: AddressResponse? = AddressResponse()
)
