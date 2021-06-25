import org.everit.json.schema.{Schema, ValidationException}
import org.json.JSONObject
import scala.jdk.CollectionConverters._
import upickle.default._
import cats.implicits._
import scala.util.{Try, Success, Failure}
import upickle_import.general._

object SchemaValidation extends ValidationTrait {
  final def apply(
      endpointName: String,
      entityText: String
  ): Validated[ujson.Value] = {
    val entityObject: JSONObject = new JSONObject(entityText)

    val schema: Schema = SchemasService.loadSchema(endpointName)

    Try(schema.validate(entityObject)) match {
      case Success(_) => {
        read[ujson.Value](entityObject.toString).validNec
      }
      case Failure(e: ValidationException) =>
        e.getCausingExceptions()
          .asScala
          .map(e => InvalidInputError(e.getMessage()).invalidNec)
          .fold(ujsonEmptyValue.validNec) { (a, b) => a <* b }
      case _ =>
        InvalidInputError(
          "Input validation failed for an unknown reason."
        ).invalidNec
    }
  }
}
