import cats.data.ValidatedNec
import upickle.default._
import cats.data.NonEmptyChain
import upickle_bundle.general._

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]

  def serialiseErrors(errors: NonEmptyChain[Error]): String = {
    write(
      errors.toChain.toList
    )
  }
}
