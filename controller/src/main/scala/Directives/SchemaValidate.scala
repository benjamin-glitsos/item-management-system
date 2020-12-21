import org.everit.json.schema.{Schema, ValidationException}
import akka.http.scaladsl.server._
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._

object SchemaValidate {
  def apply(schemaFilename: String): Directive1[String] =
    extractStrictEntity(3.seconds).map(entity => {
      val entityObject: JSONObject = new JSONObject("{ \"wow\": \"ok\" }")
      var entityBody: String       = ""

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
        entityBody = entityObject.toString()
        println(entityBody)
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

      entityBody
    })
  // TODO: in successful case, stringify the entityObject and update the body content to be that
}
