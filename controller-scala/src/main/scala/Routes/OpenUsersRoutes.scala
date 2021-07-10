import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object OpenUsersRoutes extends UpickleTrait {
  final def apply(username: String): Route = get {
    ValidationMiddleware("open-users") { body: ujson.Value =>
      complete(UsersService.open(username))
    }
  }
}
