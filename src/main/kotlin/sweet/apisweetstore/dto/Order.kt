package sweet.apisweetstore.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val dateOrder: LocalDateTime? = null,
    val nameConfectionery: String? = "",
    val valueOrder: BigDecimal? = BigDecimal.ZERO,
    val card: String? ="",
    val quantityItems: Int? = 0
)