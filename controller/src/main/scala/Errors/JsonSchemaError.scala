object JsonSchemaError {
  def apply(message: String): Error = {
    Error("incorrect_request_body_json", s"Incorrect request: $message")
  }
}
