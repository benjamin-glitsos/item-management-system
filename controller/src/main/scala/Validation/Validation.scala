import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.{SchemaClient, SchemaLoader}
import akka.http.scaladsl.server._
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import akka.http.scaladsl.model.HttpEntity
import upickle.default._
import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNec

object Validation extends ValidationTrait {
  def apply(endpointName: String): Directive1[Validated[ujson.Value]] =
    extractStrictEntity(3.seconds)
      .flatMap((entity: HttpEntity.Strict) => {
        val staticEndpoints = List("open-user")

        if (staticEndpoints contains endpointName) {
          provide(ujson.read("{}").validNec)
        } else {
          val entityObject: JSONObject = new JSONObject(entity.data.utf8String)

          val schemaSource: Source =
            Source.fromResource(s"schemas/$endpointName.json")

          val schemaString: String =
            try schemaSource.mkString
            finally schemaSource.close()

          val rawSchema: JSONObject =
            new JSONObject(
              new JSONTokener(schemaString)
            )

          val schema: Schema =
            SchemaLoader
              .builder()
              .schemaClient(SchemaClient.classPathAwareClient())
              .useDefaults(true)
              .schemaJson(rawSchema)
              .resolutionScope("classpath://schemas/")
              .draftV7Support()
              .build()
              .load()
              .build()

          var validationErrors: String = ""

          try {
            schema.validate(entityObject)
          } catch {
            case e: ValidationException => {
              val lineDelimitedErrors: String =
                e.getCausingExceptions()
                  .asScala
                  .map(e => s"Invalid input: ${e.getMessage()}")
                  .toSeq
                  .mkString("\n")

              validationErrors = lineDelimitedErrors
            }
          }

          val entityJson: ujson.Value =
            ujson.read(entityObject.toString())

          if (validationErrors.isEmpty) {
            provide(entityJson.validNec)
          } else {
            reject(ValidationRejection(validationErrors))
          }
        }
      })
}
