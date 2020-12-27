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
import scala.util.{Try, Success, Failure}

object Validation extends ValidationTrait {
  def apply(endpointName: String): Directive1[ujson.Value] =
    extractStrictEntity(3.seconds)
      .flatMap((entity: HttpEntity.Strict) => {
        val staticEndpoints = List("open-user")

        if (staticEndpoints contains endpointName) {
          provide(ujson.read("{}"))
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

          Try(schema.validate(entityObject)) match {
            case Success(_) => {
              val validatedJson: ujson.Value =
                ujson.read(entityObject.toString())
              provide(validatedJson)
            }
            case Failure(e) =>
              reject(
                ValidationRejection("")
              )
          }

          // try {} catch {
          //   case e: ValidationException =>
          //     e.getCausingExceptions()
          //       .asScala
          //       .map(_.getMessage())
          //       .foreach(validationErrors :+ JsonSchemaError(_))
          //   // TODO: no! map Try to Validated then match on Valid and Invalid to do this instead!
          // }
          //
          // val entityJson: ujson.Value =
          //   ujson.read(entityObject.toString())
          //
          // if (validationErrors.isEmpty) {
          //   provide(entityJson.validNec)
          // } else {
          //   reject(
          //     ValidationRejection(validationErrors)
          //   )
          // }
        }
      })
}
