object AuthorisationFailedError {
  def apply(): Error = {
    Error("authorisation_failed", "Authorisation failed.")
  }
}
