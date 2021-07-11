object AuthorisationFailedError extends ErrorMixin {
  final def apply(): Error = {
    Error(
      AUTHORISATION_FAILED,
      "Authorisation failed",
      "The authorisation attempt was not approved."
    )
  }
}
