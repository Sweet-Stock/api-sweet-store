package sweet.apisweetstore.integration

import com.google.gson.Gson
import feign.Response
import feign.Util
import feign.codec.ErrorDecoder
import sweet.apisweetstore.exception.FlowException

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