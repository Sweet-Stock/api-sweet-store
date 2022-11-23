package sweet.apisweetstore.mapper.payment

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.response.PaymentResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Payment

@Component
class PaymentListModelToListResponse : Mapper<List<Payment>, List<PaymentResponse>> {

    override fun map(t: List<Payment>): List<PaymentResponse> =
        t.map {
            PaymentResponse(
                it.idPayment,
                it.uuidUser,
                it.cardNumber,
                it.expirationDate,
                it.cardHolderName,
                it.cvcCode,
                it.type,
                it.brand,
                it.paymentName
            )
        }

}