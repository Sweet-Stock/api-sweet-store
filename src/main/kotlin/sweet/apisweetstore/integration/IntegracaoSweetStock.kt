package sweet.apisweetstore.integration

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sweet.apisweetstore.exception.FlowException


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



