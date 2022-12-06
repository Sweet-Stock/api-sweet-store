package sweet.apisweetstore.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

//@ControllerAdvice
//class ApplicationExceptionHandler : ResponseEntityExceptionHandler() {
//
//    @ExceptionHandler(Exception::class)
//    fun handleException(exception: Exception): ResponseEntity<*>? {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<Any>(
//            DefaultError(
//                code = HttpStatus.BAD_REQUEST.value(),
//                message = exception.message ?: ""
//            )
//        )
//    }
//
//    @ExceptionHandler(FlowException::class)
//    fun handleException(exception: FlowException): ResponseEntity<*>? {
//        return ResponseEntity.status(exception.statusCode!!).body<Any>(
//            DefaultError(
//                code = exception.statusCode!!,
//                message = exception.message
//            )
//        )
//    }
//}