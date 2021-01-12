object InvalidInputError {
  final def apply(message: String): Error = {
    Error(ErrorCodes.INVALID_INPUT, message)
  }
}
