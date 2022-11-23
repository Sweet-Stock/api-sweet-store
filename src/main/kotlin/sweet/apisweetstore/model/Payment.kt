package sweet.apisweetstore.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idPayment: Int? = null,
    val uuidUser: String,
    var cardNumber: String,
    val expirationDate: String,
    val cardHolderName: String,
    val cvcCode: String,
    val type: String,
    val brand: String,
    val paymentName: String,
    val creationDate: LocalDateTime? = LocalDateTime.now(),
)