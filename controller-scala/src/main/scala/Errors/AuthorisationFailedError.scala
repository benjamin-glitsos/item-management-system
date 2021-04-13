object AuthorisationFailedError extends ErrorTrait {
  final def apply(): Error = {
    Error(
      AUTHORISATION_FAILED,
      "Authorisation failed",
      "The authorisation attempt was not approved."
    )
  }
}
