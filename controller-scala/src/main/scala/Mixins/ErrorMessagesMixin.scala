import org.typelevel.ci.{CIString => CaseInsensitive}

trait ErrorMessagesMixin {
  final def internalServerError(): Error = Error(
    CaseInsensitive("internal_server_error"),
    "Internal Server Error",
    "An internal server error has occurred."
  )

  final def notFoundError(): Error = Error(
    CaseInsensitive("not_found"),
    "Not Found",
    "The requested resource was not found at this location."
  )

  final def invalidInputError(description: String): Error =
    Error(CaseInsensitive("invalid_input"), "Invalid Input", description)
}
