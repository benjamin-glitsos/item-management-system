import upickle.default._

trait ServiceTrait {
  def createDataOutput(data: ujson.Value): String = write(
    ujson.Obj(
      "data" -> data
    )
  )
}
