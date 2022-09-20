package sweet.apisweetstore.service

import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.dto.response.UserResponse
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

        if(userRepository.countById()){
            userRepository.save(userModel)
        }
        return userModelToResponse.map(userModel)
    }
}