package sweet.apisweetstore.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_address")
data class Address(
    @Id
    val uuid: String? = UUID.randomUUID().toString(),
    val city: String,
    val complement: String? = "",
    val neighborhood: String? = "",
    val number: Int,
    val state: String,
    val street: String,
    val cep: String,
    val dateCreate: LocalDateTime? = LocalDateTime.now(),
    val dateUpdate: LocalDateTime? = LocalDateTime.now(),
)
