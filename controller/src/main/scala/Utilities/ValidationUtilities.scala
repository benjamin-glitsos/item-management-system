import cats.data.ValidatedNel

trait ValidationUtilities {
    type Validation[A] = ValidatedNel[Error, A]
}
