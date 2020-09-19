case class Error(
    code: String,
    message: String,
    entity: Option[String],
    field: Option[String]
)
