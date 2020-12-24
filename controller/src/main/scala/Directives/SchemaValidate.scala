import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.SchemaClient
import akka.http.scaladsl.server._
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._

object SchemaValidate {
  def apply(schemaFilename: String): Directive1[String] =
    extractStrictEntity(3.seconds)
      .flatMap(entity => {
        val entityObject: JSONObject = new JSONObject(entity.data.utf8String)

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
            .schemaClient(SchemaClient.classPathAwareClient())
            .useDefaults(true)
            .schemaClient(SchemaClient.classPathAwareClient())
            .schemaJson(rawSchema)
            .resolutionScope("classpath://schemas/")
            .draftV7Support()
            .build()
            .load()
            .build()

        var validationErrors = ""

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

        if (validationErrors.isEmpty) {
          provide(entityObject.toString())
        } else {
          reject(ValidationRejection(validationErrors))
        }
      })
}
