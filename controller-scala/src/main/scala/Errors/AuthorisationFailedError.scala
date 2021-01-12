object AuthorisationFailedError {
  final def apply(): Error = {
    Error(
      ErrorCodes.AUTHORISATION_FAILED,
      "Authorisation failed",
      "The authorisation attempt was not approved."
    )
  }
}
