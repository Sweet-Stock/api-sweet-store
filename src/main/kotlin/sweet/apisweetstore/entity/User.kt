package sweet.apisweetstore.entity

import sweet.apisweetstore.entity.enums.AuthType
import sweet.apisweetstore.entity.enums.ProfileType
import java.util.*
import javax.persistence.*

@Entity
data class User(
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
    var address: Address? = null,
)
