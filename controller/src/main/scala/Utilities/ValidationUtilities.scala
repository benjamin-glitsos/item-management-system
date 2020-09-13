import cats.data.ValidatedNel

trait ValidationUtilities {
    type Validated[A] = ValidatedNel[Error, A]

    def generateCode(prefix: String, number: Int) = prefix.toUpperCase + number.toString
}
