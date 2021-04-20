import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.{SchemaClient, SchemaLoader}
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.util.Using
import scala.jdk.CollectionConverters._
import upickle.default._
import cats.implicits._
import upickle_import.general._

object SchemasDAO {
  final def load(name: String): Schema = {
    val schema: JSONObject = (Using(
      Source.fromFile(s"/usr/src/app/public/schemas/$name.json")
    ) { source =>
      new JSONObject(
        new JSONTokener(source.mkString)
      )
    }).getOrElse(new JSONObject())

    SchemaLoader
      .builder()
      .useDefaults(true)
      .schemaJson(schema)
      .draftV7Support()
      .build()
      .load()
      .build()
  }
}
