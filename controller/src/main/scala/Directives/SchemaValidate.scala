import org.everit.json.schema.{Schema, ValidationException}
import akka.http.scaladsl.server._
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import akka.http.scaladsl.model._
import scala.util.{Try, Success, Failure}

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
            .useDefaults(true)
            .schemaJson(rawSchema)
            .draftV7Support()
            .build()
            .load()
            .build()

        val validate = Try(schema.validate(entityObject))

        if (validate.isSuccess) {
          provide(entityObject.toString())
        } else {
          reject(ValidationRejection("wow"))
        }
      })
}
