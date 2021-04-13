import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_import.general._

object OpenUsersRoutes {
  final def apply(username: String): Route = get {
    Validation("open-user") { body: ujson.Value =>
      complete(UsersService.open(username))
    }
  }
}
