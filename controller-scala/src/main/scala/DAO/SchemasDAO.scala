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
  final def get(name: String): Schema = {
    val directory = "schemas"
    val classpath = s"classpath://$directory/"
    val filepath  = s"$directory/$endpointName.json"

    val source: Source =
      Source.fromResource(filepath)

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
      .schemaClient(SchemaClient.classPathAwareClient())
      .useDefaults(true)
      .schemaJson(rawSchema)
      .resolutionScope(classpath)
      .draftV7Support()
      .build()
      .load()
      .build()
  }
}
