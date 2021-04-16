import upickle.default._
import upickle_import.general._
import org.everit.json.schema.{Schema}

object SchemasService {
  private final def load(name: String): Schema = SchemasDAO.load(name: String)

  final def loadSchema(name: String): Schema = load(name)

  final def loadJson(name: String): Schema =
    read[ujson.Value](load(name).toString())
}
