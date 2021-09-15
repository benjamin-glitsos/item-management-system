import cats.data.ValidatedNec

trait ValidationMixin {
  type Validation[A] = ValidatedNec[Error, A]
}
