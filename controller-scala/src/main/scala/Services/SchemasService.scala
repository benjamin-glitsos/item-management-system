import upickle.default._
import upickle_import.general._
import org.everit.json.schema.{Schema}

object SchemasService extends ServiceTrait {
  private final def load(name: String): Schema = SchemasDAO.load(name: String)

  final def loadSchema(name: String): Schema = load(name)

  final def loadJson(name: String, includeTitle: Boolean): ujson.Value = {
    val json = read[ujson.Value](load(name).toString())
    if (!includeTitle) {
      json.obj.remove("title")
    }
    json
  }
}
