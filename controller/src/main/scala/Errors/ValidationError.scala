object ValidationError {
  def apply(message: String): Error = {
    Error("validation_failed", s"Validation Failed: $message")
  }
}
