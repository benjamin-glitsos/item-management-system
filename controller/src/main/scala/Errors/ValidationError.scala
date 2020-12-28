object ValidationError {
  final def apply(message: String): Error = {
    Error("validation_failed", message)
  }
}
