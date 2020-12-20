import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import doobie.implicits._
import bundles.doobie.connection._

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

  def list(reqBody: String): String = {
    val body: ujson.Value = ujson.read(entityBody)
    val offset            = body("offset").num
    val pageLength        = body("page_length").num
    write(
        UsersDAO
          .list(offset, pageLength)
          .transact(xa)
          .unsafeRunSync
    )
  }
}
