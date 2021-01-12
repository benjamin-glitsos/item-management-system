object NotFoundError {
  final def apply(): Error = {
    Error(ErrorCodes.NOT_FOUND, "The requested resource was not found.")
  }
}
