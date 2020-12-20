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

    try {
      val schemaSource = Source.fromResource("schemas/users-list.json")
      val schemaString =
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
      val testInput = new JSONObject("{}")
      schema.validate(testInput)
      println(testInput.get("offset"))
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

    // TODO: pass the succesful validation (with default values) to ujson, reject unsuccessful with your standardised error format

    val body: ujson.Value = ujson.read(entityBody)
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
