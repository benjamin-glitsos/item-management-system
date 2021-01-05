object AuthorisationFailedError extends ErrorTrait {
  final def apply(): Error = {
    error("authorisation_failed", "Authorisation failed.")
  }
}
