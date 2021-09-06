import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Try, Success, Failure}
import org.everit.json.schema.{Schema, ValidationException}
import org.json.JSONObject
import scala.jdk.CollectionConverters._
import upickle.default._
import cats.implicits._

object SchemaValidation
    extends ValidationMixin
    with UpickleMixin
    with ErrorMessagesMixin {
  final def apply(
      actionKey: String,
      entityText: String
  ): Validation[ujson.Value] = Future {
    val entityObject: JSONObject = new JSONObject(entityText)

    val schema: Schema = SchemasService.loadSchema(actionKey)

    Try(schema.validate(entityObject)) match {
      case Success(_) => {
        read[ujson.Value](entityObject.toString).validNec
      }
      case Failure(e: ValidationException) =>
        e.getCausingExceptions()
          .asScala
          .map(e => invalidInputError(e.getMessage()).invalidNec)
          .fold(ujsonEmptyValue.validNec) { (a, b) => a <* b }
      case Failure(e: Throwable) => throw e
    }
  }
}
