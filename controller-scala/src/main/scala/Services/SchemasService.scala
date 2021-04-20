import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.{SchemaClient, SchemaLoader}
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import upickle.default._
import cats.implicits._
import upickle_import.general._
import org.everit.json.schema.{Schema}

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
    val schemaUjson: ujson.Value = read[ujson.Value](load(name).toString())
    if (!isFull) {
      schemaUjson.obj.remove("title")
      schemaUjson.obj.remove("description")
    }
    schemaUjson
  }
}
