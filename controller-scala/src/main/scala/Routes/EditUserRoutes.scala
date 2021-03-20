import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes.NoContent
import upickle_bundle.general._

object EditUserRoutes {
  final def apply(username: String): Route = get {
    Validation("open-user") { body: ujson.Value =>
      complete(NoContent, UsersServices.open(username))
    }
  }
}
