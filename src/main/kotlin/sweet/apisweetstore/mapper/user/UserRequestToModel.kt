package sweet.apisweetstore.mapper.user

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.mapper.address.AddressRequestToModel
import sweet.apisweetstore.mapper.Mapper
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