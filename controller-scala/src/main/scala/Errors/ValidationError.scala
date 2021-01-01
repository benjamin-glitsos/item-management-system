object ValidationError extends ErrorTrait {
  final def apply(message: String): Error = {
    error("validation_failed", message)
  }
}
