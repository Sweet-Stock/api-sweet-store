package sweet.apisweetstore.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sweet.apisweetstore.dto.request.PaymentRequest
import sweet.apisweetstore.dto.response.PaymentResponse
import sweet.apisweetstore.mapper.payment.PaymentListModelToListResponse
import sweet.apisweetstore.mapper.payment.PaymentRequestToModel
import sweet.apisweetstore.repository.PaymentRepository
import sweet.apisweetstore.repository.UserRepository

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val paymentRequestToModel: PaymentRequestToModel,
    private val paymentListModelToListResponse: PaymentListModelToListResponse,
    private val userRepository: UserRepository,
) {

    fun addPaymentForUser(paymentRequest: PaymentRequest): ResponseEntity<Any> {
        if(userRepository.countByUuid(paymentRequest.uuidUser) != 1L){
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.status(201).body(paymentRepository.save(paymentRequestToModel.map(paymentRequest)))
    }

    fun getPaymentsByUser(uuidUser: String): ResponseEntity<List<PaymentResponse>> {
        if(userRepository.countByUuid(uuidUser) != 1L){
            return ResponseEntity.badRequest().build()
        }

        var payments = paymentRepository.findAllByUuidUser(uuidUser)

        payments.forEach {
            if (it.cardNumber.replace("\\s".toRegex(), "").length == 16){
                it.cardNumber = "**** **** **** " + it.cardNumber.takeLast(4)
            }
        }
        return ResponseEntity.ok().body(paymentListModelToListResponse.map(payments))
    }

    fun deletePaymentsByIdPayment(idPayment: Int): ResponseEntity<Any> {
        if(paymentRepository.findById(idPayment).isEmpty){
            return ResponseEntity.badRequest().build()
        }

        paymentRepository.deleteById(idPayment)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}