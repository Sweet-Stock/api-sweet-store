package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sweet.apisweetstore.dto.request.PaymentRequest
import sweet.apisweetstore.dto.response.PaymentResponse
import sweet.apisweetstore.service.PaymentService

@RestController
@RequestMapping("/payment")
class PaymentController(
    private val paymentService: PaymentService
) {

    @PostMapping
    fun addPaymentForUser(@RequestBody paymentRequest: PaymentRequest): ResponseEntity<Any>{
        return paymentService.addPaymentForUser(paymentRequest)
    }

    @GetMapping("/{uuidUser}")
    fun getPaymentsByUser(@PathVariable uuidUser: String): ResponseEntity<List<PaymentResponse>>{
        return paymentService.getPaymentsByUser(uuidUser)
    }

    @DeleteMapping("{idPayment}")
    fun deletePaymentsByIdPayment(@PathVariable idPayment: Int): ResponseEntity<Any>{
        return paymentService.deletePaymentsByIdPayment(idPayment)
    }
}