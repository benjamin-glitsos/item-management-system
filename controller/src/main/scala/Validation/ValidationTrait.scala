import cats.data.ValidatedNec
import upickle.default._
import cats.data.NonEmptyChain

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]

  val ujsonEmptyValue: ujson.Value = ujson.write(ujson.Obj())

  def formatErrorJson(errors: NonEmptyChain[Error]): ujson.Value = {
    ujson.write(
      ujson.Obj(
        "errors" -> errors.toChain.toList.map(error =>
          ujson.Obj(
            "code"    -> error.code,
            "message" -> error.message
          )
        )
      )
    )
  }
}
