object InternalServerError extends ErrorMixin {
  final def apply(): Error = {
    Error(
      INTERNAL_SERVER_ERROR,
      "Internal Server Error",
      "A server error has occurred."
    )
  }
}
