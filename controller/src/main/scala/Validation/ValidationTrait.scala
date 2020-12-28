import cats.data.ValidatedNec
import upickle.default._
import cats.data.NonEmptyChain

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]

  def formatErrorJson(errors: NonEmptyChain[Error]): ujson.Value = {
    ujson.write(
      errors
        .map(ujson.write(_))
        .toChain
        .toList
    )
  }
}
