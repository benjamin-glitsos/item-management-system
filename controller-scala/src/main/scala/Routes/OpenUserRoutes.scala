import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_bundle.general._

object OpenUserRoutes {
  final def apply(username: String): Route = get {
    Validation("open-user") { body: ujson.Value =>
      complete(UsersService.open(username))
    }
  }
}
