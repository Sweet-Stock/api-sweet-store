package sweet.apisweetstore.integration

import feign.Response
import feign.Util
import org.springframework.cloud.openfeign.FeignClient

import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import sweet.apisweetstore.exception.FlowException
import java.lang.Exception
import com.google.gson.Gson
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.Throws


@FeignClient(
    name = "integracao-sweet-stock",
    url = "http://localhost:8080/v1/sweet-stock",
    configuration = [FeignSimpleEncoderConfig::class]
)
interface IntegracaoSweetStock {
    @Throws(FlowException::class)
    @PostMapping("/products/get-products-by-uuids", consumes = ["application/json"])
    fun getProductsByUuid(@RequestBody uuids: List<String>): ResponseEntity<List<ProductResponseAPI>>

    @Throws(FlowException::class)
    @GetMapping("/companies/get-name-company/{uuidProduct}", consumes = ["application/json"])
    fun getNameCompany(@PathVariable uuidProduct: String): ResponseEntity<String>

    @Throws(FlowException::class)
    @PutMapping("/products/{soldQuantity}/{uuid}", consumes = ["application/json"])
    fun sellProduct(@PathVariable uuid: String,@PathVariable soldQuantity: Int)
}

class FeignSimpleEncoderConfig {
    @Bean
    fun encoder(): ErrorDecoder {
        return CustomErrorDecoder()
    }
}

class CustomErrorDecoder : ErrorDecoder {

    private val defaultErrorDecoder: ErrorDecoder = ErrorDecoder.Default()
    private val defaultErrorMessage = "Ocorreu um erro."

    private data class ProblemFeign(
        var detail: String
    )

    private val warns = mapOf(
        400 to "ERROR"
    )
    override fun decode(methodKey: String?, response: Response): Exception {
        val body = Util.toString(response.body().asReader())
        return if (warns.containsKey(response.status())) {
            FlowException(body, warns[response.status()]!!)
        } else if (response.status() == 400) {
            val message = Gson().fromJson(body, ProblemFeign::class.java)
            FlowException(message.detail, "GENERIC_ERROR_CODE")
        } else if (response.status() == 500) {
            FlowException(defaultErrorMessage, "GENERIC_ERROR_CODE")
        } else {
            defaultErrorDecoder.decode(methodKey, response);
        }
    }
}