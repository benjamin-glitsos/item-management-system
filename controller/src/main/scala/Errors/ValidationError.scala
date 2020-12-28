object ValidationError {
  def apply(message: String): Error = {
    Error("validation_failed", message)
  }
}
