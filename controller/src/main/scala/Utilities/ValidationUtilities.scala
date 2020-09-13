import cats.data.ValidatedNel

trait ValidationUtilities {
    type Validated[A] = ValidatedNel[Error, A]
}
