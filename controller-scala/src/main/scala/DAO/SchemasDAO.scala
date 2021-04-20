import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.{SchemaClient, SchemaLoader}
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import upickle.default._
import cats.implicits._
import upickle_import.general._

object SchemasDAO {
  final def load(name: String): JSONObject = new JSONObject(
    new JSONTokener(FilesystemDAO.open(s"schemas/$name.json"))
  )
}
