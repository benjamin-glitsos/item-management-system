import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import upickle.default._

object SchemasService extends ServiceTrait {
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
    val schemaUjson: ujson.Value = read[ujson.Value](load(name).toString)
    if (!isFull) {
      schemaUjson.obj.remove("title")
      schemaUjson.obj.remove("description")
    }
    schemaUjson
  }
}
