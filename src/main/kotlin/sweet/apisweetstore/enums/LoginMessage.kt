package sweet.apisweetstore.enums

enum class LoginMessage(val message: String) {
    EMAIL_NOT_EXIST(message = "Email não cadastrado!"),
    WRONG_PASSWORD(message = "Senha incorreta!"),
    LOGIN_SUCCESS(message = "Login efetuado com sucesso!")
}