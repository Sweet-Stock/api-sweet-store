package sweet.apisweetstore.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Address(
    @Id
    var uuid: UUID? = UUID.randomUUID(),
    var city: String? = "",
    var complement: String? = "",
    var neighborhood: String? = "",
    var number: String? = "",
    var state: String? = "",
    var street: String? = "",
    var cep: String? = "",
)
