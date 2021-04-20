import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.{SchemaClient, SchemaLoader}
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import upickle.default._
import cats.implicits._
import scala.util.{Try, Success, Failure}
import upickle_import.general._

object SchemasDAO {
  final def load(name: String): Schema = {
    val source: Source =
      Source.fromFile(s"/usr/src/app/public/schemas/$name.json")

    val rawSchema: JSONObject = {
      val s: String =
        try source.mkString
        finally source.close()

      new JSONObject(
        new JSONTokener(s)
      )
    }

    SchemaLoader
      .builder()
      .useDefaults(true)
      .schemaJson(rawSchema)
      .draftV7Support()
      .build()
      .load()
      .build()
  }
}
