import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel
import scala.util.matching.Regex

trait ValidationUtilities {
    type Validation[A] = ValidatedNel[Error, A]

    def doesStringContainPattern(string: String, pattern: Regex, error: Error): Validation[String] = {
        pattern.findFirstMatchIn(string) match {
            case Some(_) => string.validNel
            case None => error.invalidNel
        }
    }

    def multipleValidations[A](value: A, validators: List[A => Validation[A]]) = {
        validators
            .map(f => f(value))
            .fold(value.validNel)((a: Validation[A], b: Validation[A]) => a *> b)
    }
}
