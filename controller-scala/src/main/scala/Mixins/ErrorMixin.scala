import org.typelevel.ci.{CIString => CaseInsensitive}
import cats.data.NonEmptyChain
import upickle.default._

trait ErrorMixin extends UpickleMixin {
  final val NOT_FOUND            = CaseInsensitive("not_found")
  final val INVALID_INPUT        = CaseInsensitive("invalid_input")
  final val AUTHORISATION_FAILED = CaseInsensitive("authorisation_failed")

  final def serialiseErrors(errors: NonEmptyChain[Error]): String = {
    write(
      errors.toChain.toList
    )
  }
}
