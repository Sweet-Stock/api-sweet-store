package sweet.apisweetstore.exception

@Suppress("Unused")
data class FlowException(
    override val message: String,
    var code: String? = "",
    var statusCode: Int? = 400
) :
    Exception(message)