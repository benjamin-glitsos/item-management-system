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
    val schemaJson: ujson.Value = read[ujson.Value](load(name).toString)
    if (!isFull) {
      schemaJson.obj.remove("title")
      schemaJson.obj.remove("description")
      schemaJson.obj.remove("minProperties")
    }
    ujson.Obj(
      "data" -> schemaJson
    )
  }
}
