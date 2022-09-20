package sweet.apisweetstore.service

import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.mapper.UserRequestToModel
import sweet.apisweetstore.model.User
import sweet.apisweetstore.repository.UserRepository
import sweet.apisweetstore.utils.Cryptography

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRequestToModel: UserRequestToModel
) {

    fun register(user: UserRequest){
        user.password = Cryptography.convertPasswordToSHA512(user.password)
        userRepository.save(userRequestToModel.map(user))
    }
}