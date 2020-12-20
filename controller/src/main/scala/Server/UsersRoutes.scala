import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import doobie.implicits._
import bundles.doobie.connection._
import akka.http.scaladsl.model._
import doobie._
import scala.concurrent.duration._

// TODO: make this route/UsersService use the request JSON and validate it using the everit JSON Schema package.
// TODO: make custom error response which is the same but using JSON. E.g. { errors: ["The requested object was not ..."] }
// TODO: make sure this is async and nothing is blocking

object UsersRoutes {
  def apply(): Route = extractStrictEntity(3.seconds) { entity =>
    concat(
        get(
            complete(
                HttpEntity(
                    ContentTypes.`application/json`,
                    UsersServices.list(entity.data.utf8String)
                )
            )
        )
    )
  }
}
// post(complete("Posted")),
// delete(complete("Deleted"))
