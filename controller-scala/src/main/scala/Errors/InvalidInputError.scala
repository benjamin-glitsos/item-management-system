object InvalidInputError extends ErrorMixin {
  final def apply(description: String): Error = {
    Error(INVALID_INPUT, "Invalid Input", description)
  }
}
