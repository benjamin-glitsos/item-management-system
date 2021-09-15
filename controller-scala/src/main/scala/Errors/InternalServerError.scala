import org.typelevel.ci.{CIString => CaseInsensitive}

object InternalServerError extends ErrorMixin {
  final def apply(): Error = {
    Error(
      CaseInsensitive("internal_server_error"),
      "Internal Server Error",
      "An internal server error has occurred."
    )
  }
}
