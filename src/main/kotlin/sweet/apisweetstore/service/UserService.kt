package sweet.apisweetstore.service

import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.dto.response.LoginRequest
import sweet.apisweetstore.dto.response.LoginResponse
import sweet.apisweetstore.dto.response.UserResponse
import sweet.apisweetstore.enums.LoginMessage
import sweet.apisweetstore.mapper.UserRequestToModel
import sweet.apisweetstore.mapper.UserModelToResponse
import sweet.apisweetstore.repository.UserRepository
import sweet.apisweetstore.utils.Cryptography

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRequestToModel: UserRequestToModel,
    private val userModelToResponse: UserModelToResponse
) {
    fun register(user: UserRequest): UserResponse {
        user.password = Cryptography.convertPasswordToSHA512(user.password)
        var userModel = userRequestToModel.map(user)

        if (userRepository.countByUuid(userModel.uuid.toString()) == 0L &&
            userRepository.countByEmail(userModel.email) == 0L
        ) {
            userRepository.save(userModel)
        }

        return userModelToResponse.map(userModel)
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        val emailExists = userRepository.countByEmail(loginRequest.email)

        if (emailExists == 1L) {
            var auth =
                userRepository.auth(loginRequest.email, Cryptography.convertPasswordToSHA512(loginRequest.password))

            if (auth == 1L) {
                return LoginResponse(
                    uuid = userRepository.findUuidUser(
                        loginRequest.email,
                        Cryptography.convertPasswordToSHA512(loginRequest.password)
                    ),
                    email = loginRequest.email,
                    message = LoginMessage.LOGIN_SUCESS.toString()
                )
            }

            if (auth == 0L) {
                return LoginResponse(
                    email = loginRequest.email,
                    message = LoginMessage.WRONG_PASSWORD.toString()
                )
            }
        }
        return LoginResponse(
            email = loginRequest.email,
            message = LoginMessage.EMAIL_NOT_EXIST.toString()
        )
    }

//    fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Any {
//
//    }
}