import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ServiceTrait {
  def createDataOutput(data: ujson.Value): String = write(
    ujson.Obj(
      "data" -> data
    )
  )
}
