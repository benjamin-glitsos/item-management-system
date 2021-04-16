import cats.data.ValidatedNec

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]
}
