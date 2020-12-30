object AuthorisationFailedError {
  final def apply(): Error = {
    Error("authorisation_failed", "Authorisation failed.")
  }
}
