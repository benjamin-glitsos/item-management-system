import cats.data.ValidatedNel
import cats.syntax.validated._

object ErrorUtilities {
    def optionToValidated[A](x: Option[A], error: Error.Message): Error.Validated[A] = {
        x match {
            case Some(x) => Valid(x)
            case None => Invalid(error)
        }
    }
}
