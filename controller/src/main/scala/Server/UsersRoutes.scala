import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import doobie.implicits._
import bundles.doobie.connection._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.HttpMethods._

object UsersRoutes {
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

  def apply(): Route = concat(
      get(
          complete(
              HttpEntity(
                  ContentTypes.`application/json`,
                  write(
                      UsersDAO
                        .list(offset = 0, length = 25)
                        .transact(xa)
                        .unsafeRunSync
                  )
              )
          )
      )
  )
  // TODO: make this into UsersService

  // post(complete("Posted")),
  // delete(complete("Deleted"))
}
