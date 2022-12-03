package sweet.apisweetstore.integration

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class ProductResponseAPI(
    val uuid: String? = "",
    val name: String? = "",
    val saleValue: BigDecimal? = BigDecimal.ZERO,
    val expirationDate: Date ? = null,
    val dateInsert: LocalDate ? = null,
    val dateUpdate: LocalDate ? = null,
    val isRefigerated: Boolean? = null,
    val sold: Boolean? = null,
    val total: Int? = 0,
    val unitMeasurement: String? = "",
    val category: Category? = Category(),
    val picture: String? = "",
    val nutritionalFacts: NutritionalFactsResponseAPI?
)