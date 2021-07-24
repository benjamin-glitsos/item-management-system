case class ServerError(
    timestamp: String,
    actionKey: Optional[String],
    method: String,
    uri: String,
    cause: String
)
