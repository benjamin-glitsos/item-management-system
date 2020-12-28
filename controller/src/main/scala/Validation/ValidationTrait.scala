import cats.data.ValidatedNec
import upickle.default._
import cats.data.NonEmptyChain
import upickle_bundle.implicits._

trait ValidationTrait {
  implicit val upickleError: ReadWriter[Error] = macroRW
  type Validated[A] = ValidatedNec[Error, A]

  def formatErrorJson(errors: NonEmptyChain[Error]): ujson.Value = {
    ujson.write(
      errors
        .map((e: Error) => ujson.write(ujsonEmptyValue))
        .toChain
        .toList
    )
  }
}
