package sweet.apisweetstore.enums

enum class ChangePasswordMessage(message: String) {
    SAME_PASSWORD(message = "Senha atual e antiga iguais!"),
    CURRENT_PASSWORD_INCORRECT(message = "Senha atual incorreta!"),
    CONFIRMATION_PASSWORD_INCORRECT(message = "Confirmacao de senha incorreta!"),
    CHANGE_PASSWORD_SUCCESS(message = "Reset de senha efetuado com sucesso!"),
    UUID_ERROR(message = "Nao foi possivel verificar uuid"),
    PASSWORD_ERROR(message = "Ocorreu um erro ao atualizar a senha")
}