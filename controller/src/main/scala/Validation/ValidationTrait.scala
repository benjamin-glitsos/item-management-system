import cats.data.ValidatedNec
import upickle.default._
import cats.data.NonEmptyChain
import upickle_bundle.implicits._

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]

  def serialiseErrors(errors: NonEmptyChain[Error]): ujson.Value = {
    write(
      errors
        .map(write(_))
        .toChain
        .toList
    )
  }
}
