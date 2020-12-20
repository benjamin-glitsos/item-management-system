import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._

object UsersRoutes {
  implicit val localDateTimeReadWrite: ReadWriter[LocalDateTime] =
    readwriter[ujson.Value].bimap[LocalDateTime](
        x => x.toString(),
        json => {
          val format: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
          LocalDateTime.parse(json.toString(), format)
        }
    )

  implicit val rw: ReadWriter[UsersList] = macroRW

  def apply(): Route = concat(
      get(
          complete(
              UsersDAO
                .list(offset = 0, length = 25)
                .map((x: List[UsersList]) => x.toString)
          )
      )

      // post(complete("Posted")),
      // delete(complete("Deleted"))
  )
}
