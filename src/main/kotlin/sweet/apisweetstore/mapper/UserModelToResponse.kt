package sweet.apisweetstore.mapper

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.dto.response.UserResponse
import sweet.apisweetstore.model.User

@Component
class UserModelToResponse: Mapper<User, UserResponse> {

    override fun map(t: User): UserResponse {
        return UserResponse(
            t.uuid.toString(),
            t.name,
            t.email,
            t.profile,
            t.authType
        )
    }
}