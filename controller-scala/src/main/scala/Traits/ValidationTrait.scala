import cats.data.ValidatedNec
import upickle.default._
import cats.data.NonEmptyChain
import upickle_import.general._

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]
}
