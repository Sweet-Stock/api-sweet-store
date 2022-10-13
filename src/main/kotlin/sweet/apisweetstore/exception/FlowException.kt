package sweet.apisweetstore.exception

@Suppress("Unused")
data class FlowException(override val message: String, var code: String): Exception(message)