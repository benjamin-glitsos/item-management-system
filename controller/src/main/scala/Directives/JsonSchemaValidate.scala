import org.everit.json.schema.{Schema, ValidationException}
import akka.http.scaladsl.server.ValidationRejection
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._

object JsonSchemaValidate {
  def apply(schemaFilename: String): Directive0 =
    extractStrictEntity(3.seconds) { entity =>
      val entityObject: JSONObject = new JSONObject(entity.data.utf8String)

      try {
        val schemaSource: Source =
          Source.fromResource(s"schemas/$schemaFilename.json")

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
            .useDefaults(true)
            .schemaJson(rawSchema)
            .draftV7Support()
            .build()
            .load()
            .build()

        schema.validate(entityObject)
        pass

      } catch {
        case e: ValidationException => {
          val validationIntro: String = e.getMessage()

          val validationErrors: Seq[String] =
            e.getCausingExceptions().asScala.map(_.getMessage()).toSeq

          val validationAll: Seq[String] =
            validationIntro +: validationErrors

          reject(ValidationRejection(validationAll.mkString("\n")))
        }
        case e: Throwable => reject(ValidationRejection(e.toString()))
      }

      cancelRejections(classOf[ValidationRejection])
    }
  // TODO: in successful case, stringify the entityObject and update the body content to be that
}
