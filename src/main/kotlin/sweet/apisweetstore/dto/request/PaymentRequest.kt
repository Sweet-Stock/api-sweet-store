package sweet.apisweetstore.dto.request


data class PaymentRequest(
    val uuidUser: String,
    val cardNumber: String,
    val expirationDate: String,
    val cardHolderName: String,
    val cvcCode: String,
    val type: String,
    val brand: String,
    val paymentName: String,
)
