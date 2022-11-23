package sweet.apisweetstore.mapper.payment

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.PaymentRequest
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Payment
import java.time.LocalDateTime

@Component
class PaymentRequestToModel : Mapper<PaymentRequest, Payment> {

    override fun map(t: PaymentRequest): Payment =
        Payment(
            idPayment = null,
            uuidUser = t.uuidUser,
            cardNumber = t.cardNumber,
            expirationDate = t.expirationDate,
            cardHolderName = t.cardHolderName,
            cvcCode = t.cvcCode,
            type = t.type,
            brand = t.brand,
            creationDate = LocalDateTime.now(),
            paymentName = t.paymentName
        )
}