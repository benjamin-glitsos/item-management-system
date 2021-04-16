import cats.data.ValidatedNec
import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.{SchemaClient, SchemaLoader}
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import upickle.default._
import cats.implicits._
import scala.util.{Try, Success, Failure}
import upickle_import.general._

trait ValidationTrait {
  type Validated[A] = ValidatedNec[Error, A]

  val schemaPath: String = "classpath://schemas/"
}
