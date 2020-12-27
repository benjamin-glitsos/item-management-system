import cats.data.ValidatedNec
import upickle.default._
import cats.data.NonEmptyChain

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]

  def formatErrorJson(errors: NonEmptyChain[Error]): ujson.Value = {
    ujson.write(
      errors
        .map(error =>
          ujson.Obj(
            "code"    -> error.code,
            "message" -> error.message
          )
        )
        .toChain
        .toList
    )
  }
}
