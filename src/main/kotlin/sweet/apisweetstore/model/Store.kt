package sweet.apisweetstore.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "tb_store")
data class Store(
        @Id
        val uuid: String? = UUID.randomUUID().toString(),
        val name: String,
        val cnpj: String,
        val email: String,
        val phone: String,
        val image: String? = "",
        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn(name = "address_uuid", referencedColumnName = "uuid")
        val address: Address,
        val dateCreate: LocalDateTime? = LocalDateTime.now(),
        val dateUpdate: LocalDateTime? = LocalDateTime.now(),

        )
