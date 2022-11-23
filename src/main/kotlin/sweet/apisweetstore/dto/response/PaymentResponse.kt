package sweet.apisweetstore.dto.response

data class PaymentResponse(
    val idPayment: Int? = 0,
    val uuidUser: String? = "",
    val cardNumber: String? = "",
    val expirationDate: String? = "",
    val cardHolderName: String? = "",
    val cvcCode: String? = "",
    val type: String? = "",
    val brand: String? = "",
    val paymentName: String? = "",
)