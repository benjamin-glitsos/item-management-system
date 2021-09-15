import org.typelevel.ci.{CIString => CaseInsensitive}

object InvalidInputError extends ErrorMixin {
  final def apply(description: String): Error = {
    Error(CaseInsensitive("invalid_input"), "Invalid Input", description)
  }
}
