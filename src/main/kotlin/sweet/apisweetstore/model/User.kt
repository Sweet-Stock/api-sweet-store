package sweet.apisweetstore.model

import sweet.apisweetstore.dto.request.AddressRequest
import sweet.apisweetstore.enums.AuthType
import sweet.apisweetstore.enums.ProfileType
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_user")
data class User(
    @Id
    val uuid: String? = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val image: String? = null,
    val phone: String,
    val profile: ProfileType? = ProfileType.MODERATE,
    var password: String,
    val authType: AuthType? = AuthType.DEFAULT,

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "address_uuid", referencedColumnName = "uuid")
    val address: Address? = Address(),

    val dateCreate: LocalDateTime? = LocalDateTime.now(),
    val dateUpdate: LocalDateTime? = LocalDateTime.now(),
)