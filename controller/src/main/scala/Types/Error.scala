import cats.data.ValidatedNel

object Error {
    type Message = String

    type Validated[A] = ValidatedNel[Message, A]
}
