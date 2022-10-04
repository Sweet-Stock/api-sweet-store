package sweet.apisweetstore.dto.request

import sweet.apisweetstore.enums.AuthType
import sweet.apisweetstore.enums.ProfileType


data class UserRequest(
    val name: String,
    val email: String,
    val image: String? = null,
    val phone: String,
    val profile: ProfileType? = ProfileType.MODERATE,
    var password: String,
    val authType: AuthType? = AuthType.DEFAULT,
    val address: AddressRequest? = AddressRequest()
)