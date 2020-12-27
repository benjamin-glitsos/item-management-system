object JsonSchemaError {
  def apply(message: String) = {
    Error("incorrect_request_body_json", message)
  }
}
