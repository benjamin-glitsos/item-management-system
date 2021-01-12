object NotFoundError {
  final def apply(): Error = {
    Error(
      ErrorCodes.NOT_FOUND,
      "Not Found",
      "The requested resource was not found at this location."
    )
  }
}
