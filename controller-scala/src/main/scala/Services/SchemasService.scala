import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import upickle.default._
import scala.util.Try

object SchemasService extends ServiceMixin {
  private final def load(name: String): JSONObject = SchemasDAO.load(name)

  final def loadSchema(name: String): Schema = {
    val schemaJsonObj: JSONObject = load(name)
    SchemaLoader
      .builder()
      .useDefaults(true)
      .schemaJson(schemaJsonObj)
      .draftV7Support()
      .build()
      .load()
      .build()
  }

  final def loadJson(name: String, isFull: Boolean): ujson.Value = {
    val data: ujson.Value = read[ujson.Value](load(name).toString)
    if (!isFull) {
      data.obj.remove("title")
      data.obj.remove("description")
      data.obj.remove("minProperties")
    }
    if (Try(data("properties")("password").obj).isSuccess) {
      data("properties").obj.remove("password")
    }
    ujson.Obj(
      "data" -> data
    )
  }
}
