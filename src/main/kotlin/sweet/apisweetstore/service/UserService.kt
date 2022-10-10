package sweet.apisweetstore.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.ChangePasswordRequest
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.dto.response.ChangePasswordResponse
import sweet.apisweetstore.dto.request.LoginRequest
import sweet.apisweetstore.dto.response.LoginResponse
import sweet.apisweetstore.dto.response.UserResponse
import sweet.apisweetstore.enums.ChangePasswordMessage
import sweet.apisweetstore.enums.LoginMessage
import sweet.apisweetstore.mapper.user.UserRequestToModel
import sweet.apisweetstore.mapper.user.UserModelToResponse
import sweet.apisweetstore.model.User
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
                    message = LoginMessage.LOGIN_SUCCESS.message
                )
            }

            if (auth == 0L) {
                return LoginResponse(
                    email = loginRequest.email,
                    message = LoginMessage.WRONG_PASSWORD.message
                )
            }
        }
        return LoginResponse(
            email = loginRequest.email,
            message = LoginMessage.EMAIL_NOT_EXIST.message
        )
    }
    fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse {
        if (userRepository.countByUuid(changePasswordRequest.uuid) == 0L) {
            return ChangePasswordResponse(ChangePasswordMessage.UUID_ERROR.message)
        }
        if (userRepository
                .verifyActualPassword(
                    changePasswordRequest.uuid,
                    Cryptography.convertPasswordToSHA512(changePasswordRequest.actualPassword)
                ) == 0L
        ) {
            return ChangePasswordResponse(ChangePasswordMessage.CURRENT_PASSWORD_INCORRECT.message)
        }

        if (changePasswordRequest.actualPassword == changePasswordRequest.newPassword) {
            return ChangePasswordResponse(ChangePasswordMessage.SAME_PASSWORD.message)
        }

        if (changePasswordRequest.newPassword != changePasswordRequest.newPasswordConfirmation) {
            return ChangePasswordResponse(ChangePasswordMessage.CONFIRMATION_PASSWORD_INCORRECT.message)
        }

        if (userRepository.changePassword(
                Cryptography.convertPasswordToSHA512(changePasswordRequest.newPassword),
                changePasswordRequest.uuid
            ) != 1
        ) {
            return ChangePasswordResponse(ChangePasswordMessage.PASSWORD_ERROR.message)
        }
        return ChangePasswordResponse(ChangePasswordMessage.CHANGE_PASSWORD_SUCCESS.message)
    }

    fun updateProfile(user: UserRequest, uuid: String):ResponseEntity<Unit>{
      var userToUpdate: User = userRepository.findByUuid(uuid)
        if (userToUpdate == null){
         return ResponseEntity.noContent().build()
      }

        userToUpdate.email = user.email
        userToUpdate.name = user.name
        userToUpdate.phone = user.phone
        userToUpdate.password = user.password
        userToUpdate.image = user.image
        userToUpdate.profile = user.profile

        userRepository.save(userToUpdate)
       return ResponseEntity.ok().build()
    }
}