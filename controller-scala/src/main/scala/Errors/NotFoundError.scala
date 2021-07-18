object NotFoundError extends ErrorMixin {
  final def apply(): Error = {
    Error(
      NOT_FOUND,
      "Not Found",
      "The requested resource was not found at this location."
    )
  }
}
