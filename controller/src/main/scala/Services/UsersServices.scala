import doobie._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import doobie.implicits._
import bundles.doobie.connection._
import akka.http.scaladsl.model._

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

  def list(s: String): String = {
    println(s)
    write(
        UsersDAO
          .list(offset = 0, pageLength = 25)
          .transact(xa)
          .unsafeRunSync
    )
  }
}
