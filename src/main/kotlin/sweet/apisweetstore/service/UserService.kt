package sweet.apisweetstore.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
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
    fun register(user: UserRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<UserResponse> {
        user.password = Cryptography.convertPasswordToSHA512(user.password)

        val userModel = userRequestToModel.map(user)

        if (userRepository.countByUuid(userModel.uuid.toString()) != 0L) return ResponseEntity.status(400).build()

        if (userRepository.countByEmail(userModel.email) != 0L) return ResponseEntity.status(400).build()

        userRepository.save(userModel)

        return ResponseEntity
            .created(uriBuilder.path("/user/${userModel.uuid}").build().toUri())
            .body(userModelToResponse.map(userModel))
    }

    fun login(loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        if (userRepository.countByEmail(loginRequest.email) == 1L) {
            val auth = userRepository.auth(
                loginRequest.email,
                Cryptography.convertPasswordToSHA512(loginRequest.password)
            )

            if (auth == 1L) return ResponseEntity.status(200).body(
                LoginResponse(
                    uuid = userRepository.findUuidUser(
                        loginRequest.email,
                        Cryptography.convertPasswordToSHA512(loginRequest.password)
                    ),
                    email = loginRequest.email,
                    message = LoginMessage.LOGIN_SUCCESS.message
                )
            )

            if (auth == 0L) return ResponseEntity.status(400).body(
                LoginResponse(
                    email = loginRequest.email,
                    message = LoginMessage.WRONG_PASSWORD.message
                )
            )

        }

        return ResponseEntity.status(404).body(
            LoginResponse(
                email = loginRequest.email,
                message = LoginMessage.EMAIL_NOT_EXIST.message
            )
        )
    }

    fun changePassword(changePasswordRequest: ChangePasswordRequest): ResponseEntity<ChangePasswordResponse> {
        if (userRepository.countByUuid(changePasswordRequest.uuid) == 0L) return ResponseEntity
            .status(400)
            .body(ChangePasswordResponse(ChangePasswordMessage.UUID_ERROR.message))

        if (userRepository.verifyActualPassword(
                changePasswordRequest.uuid,
                Cryptography.convertPasswordToSHA512(changePasswordRequest.actualPassword)
            ) == 0L
        ) return ResponseEntity.status(400)
            .body(ChangePasswordResponse(ChangePasswordMessage.CURRENT_PASSWORD_INCORRECT.message))

        if (changePasswordRequest.actualPassword == changePasswordRequest.newPassword) return ResponseEntity.status(400)
            .body(ChangePasswordResponse(ChangePasswordMessage.SAME_PASSWORD.message))

        if (changePasswordRequest.newPassword != changePasswordRequest.newPasswordConfirmation) return ResponseEntity
            .status(400)
            .body(ChangePasswordResponse(ChangePasswordMessage.CONFIRMATION_PASSWORD_INCORRECT.message))

        if (userRepository.changePassword(
                Cryptography.convertPasswordToSHA512(changePasswordRequest.newPassword),
                changePasswordRequest.uuid
            ) != 1
        ) return ResponseEntity.status(400)
            .body(ChangePasswordResponse(ChangePasswordMessage.PASSWORD_ERROR.message))

        return ResponseEntity.status(200)
            .body(ChangePasswordResponse(ChangePasswordMessage.CHANGE_PASSWORD_SUCCESS.message))
    }

    fun updateProfile(user: UserRequest, uuid: String): ResponseEntity<Unit> {
        val userToUpdate: User = userRepository.findByUuid(uuid)

        userRepository.save(
            User(
                email = user.email,
                name = user.name,
                phone = user.phone,
                password = user.password,
                image = user.image,
                profile = user.profile
            )
        )
        return ResponseEntity.ok().build()
    }
}