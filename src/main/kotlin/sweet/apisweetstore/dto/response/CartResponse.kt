package sweet.apisweetstore.dto.response

import java.time.LocalDateTime

data class CartResponse(
    val idItem: Int? = 0,
    val uuidProduct: String? = "",
    val uuidCompany: String? ="",
    val quantityProduct: Int? = 0,
    val dateAddition: LocalDateTime? = null,
    val message: String? = "",
)
