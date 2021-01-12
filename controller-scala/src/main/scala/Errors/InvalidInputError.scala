object InvalidInputError {
  final def apply(description: String): Error = {
    Error(ErrorCodes.INVALID_INPUT, "Invalid Input", description)
  }
}
