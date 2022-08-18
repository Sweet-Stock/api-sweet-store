package sweet.apisweetstorage.entity

import sweet.apisweetstorage.entity.enums.AuthType
import sweet.apisweetstorage.entity.enums.ProfileType
import java.util.*
import javax.persistence.*

@Entity
data class UserEntity(
    @Id
    var uuid: UUID? = UUID.randomUUID(),
    var name: String? = "",
    var email: String? = "",
    var image: String? = "",
    var phone: String? = "",
    var profile: ProfileType? = ProfileType.MODERATE,
    var password: String? = "",
    var authType: AuthType? = AuthType.DEFAULT,

    @OneToOne
    @JoinColumn(name = "uuid")
    var address: AddressEntity? = null,
)
