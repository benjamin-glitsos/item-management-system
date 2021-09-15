import org.typelevel.ci.{CIString => CaseInsensitive}

object NotFoundError extends ErrorMixin {
  final def apply(): Error = {
    Error(
      CaseInsensitive("not_found"),
      "Not Found",
      "The requested resource was not found at this location."
    )
  }
}
