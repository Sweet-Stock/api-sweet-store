package sweet.apisweetstore.dto.request
data class ChangePasswordRequest(
    val uuid: String,
    val actualPassword: String,
    val newPassword: String,
    val newPasswordConfirmation: String
)
