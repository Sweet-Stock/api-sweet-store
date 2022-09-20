package sweet.apisweetstore.mapper

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.enums.AuthType
import sweet.apisweetstore.enums.ProfileType
import sweet.apisweetstore.model.Address
import sweet.apisweetstore.model.User

@Component
class UserRequestToModel(
    private val addressRequestToModel: AddressRequestToModel
): Mapper<UserRequest, User> {

    override fun map(t: UserRequest): User {
        return User(
            name = t.name,
            email = t.email,
            image = t.image,
            phone = t.phone,
            profile = t.profile,
            password = t.password,
            authType = t.authType,
            address = t.address?.let { addressRequestToModel.map(it) }
        )
    }
}