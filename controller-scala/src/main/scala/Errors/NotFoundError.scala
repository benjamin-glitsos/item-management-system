object NotFoundError extends ErrorTrait {
  final def apply(): Error = {
    error("not_found", "The requested resource was not found.")
  }
}
