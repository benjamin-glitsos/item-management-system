import cats.data.ValidatedNec

trait ValidationMixin {
  type Validated[A] = ValidatedNec[Error, A]
}
