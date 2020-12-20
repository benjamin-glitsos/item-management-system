import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import doobie.implicits._
import bundles.doobie.connection._
import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._

object UsersServices {
  implicit val localDateTimeReadWrite: ReadWriter[LocalDateTime] =
    readwriter[ujson.Value].bimap[LocalDateTime](
        x => x.toString(),
        json => {
          val defaultFormat: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
          LocalDateTime.parse(json.toString(), defaultFormat)
        }
    )

  implicit val rw: ReadWriter[UsersList] = macroRW

  def list(entityBody: String): String = {
    val entityObject: JSONObject = new JSONObject(entityBody)

    try {
      val schemaSource: Source = Source.fromResource("schemas/users-list.json")
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

    } catch {
      case e: ValidationException => {
        val validationIntro: String = e.getMessage()
        val validationErrors: Seq[String] =
          e.getCausingExceptions().asScala.map(_.getMessage()).toSeq
        val validationAll: Seq[String] =
          validationIntro +: validationErrors
        val messages: String =
          validationAll.mkString("\n")
        println(messages)
      }
      case e: Throwable => println(e)
    }

    val body: ujson.Value = ujson.read(entityObject.toString())
    val offset            = body("offset").num.toInt
    val pageLength        = body("page_length").num.toInt

    write(
        UsersDAO
          .list(offset, pageLength)
          .transact(xa)
          .unsafeRunSync
    )
  }
}
