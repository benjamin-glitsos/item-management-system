object InvalidInputError extends ErrorTrait {
  final def apply(description: String): Error = {
    Error(INVALID_INPUT, "Invalid Input", description)
  }
}
