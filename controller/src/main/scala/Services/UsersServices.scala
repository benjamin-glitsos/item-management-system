import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import doobie.implicits._
import bundles.doobie.connection._
import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import scala.io.Source

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
    val schemaSource = Source.fromResource("schemas/users-list.json")
    val schemaString =
      try schemaSource.mkString
      finally schemaSource.close()
    val rawSchema: JSONObject = new JSONObject(schemaString)
    val schema: Schema        = SchemaLoader.load(rawSchema)
    println(
        schema.validate(
            new JSONObject(entityBody)
        )
    )

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
