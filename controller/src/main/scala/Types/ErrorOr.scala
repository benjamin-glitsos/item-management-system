import cats.Applicative
import cats.data.ValidatedNel
import cats.syntax.validated._

type ErrorOr[A] = ValidatedNel[Error, A]
