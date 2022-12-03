package sweet.apisweetstore.dto.response

import sweet.apisweetstore.model.Address
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val idOrder: Int? = 0,
    val statusOrder: String? = "",
    val dateOrder: LocalDateTime? = null,
    val nameConfectionery: String? = "",
    val valueOrder: BigDecimal? = BigDecimal.ZERO,
    val card: String? = "",
    val brandCard: String? = "",
    val quantityItems: Int? = 0,
    val nameUser: String ? = "",
    val address: AddressResponse ? = AddressResponse()
)