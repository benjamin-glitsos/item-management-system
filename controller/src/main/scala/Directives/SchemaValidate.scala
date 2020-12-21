import org.everit.json.schema.{Schema, ValidationException}
import akka.http.scaladsl.server._
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}

object SchemaValidate {
  def apply(schemaFilename: String): Directive1[String] =
    extractStrictEntity(3.seconds).map(entity => {
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
          .useDefaults(true)
          .schemaJson(rawSchema)
          .draftV7Support()
          .build()
          .load()
          .build()

      Try(schema.validate(entityObject)) match {
        case Success(_) => pass
        case Failure(e) => {
          // val validationIntro: String = e.getMessage()
          //
          // val validationErrors: Seq[String] =
          //   e.getCausingExceptions().asScala.map(_.getMessage()).toSeq
          //
          // val validationAll: Seq[String] =
          //   validationIntro +: validationErrors
          //
          // reject(ValidationRejection(validationAll.mkString("\n")))
          println(e.getMessage())
          e
        }
      }

      entityObject.toString()
    })
}
