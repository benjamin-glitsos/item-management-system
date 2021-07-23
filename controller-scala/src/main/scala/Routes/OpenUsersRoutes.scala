import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object OpenUsersRoutes extends UpickleMixin {
  final def apply(username: String): Route = get {
    (SetActionKeyMiddleware("open-users") & ValidationMiddleware()) {
      body: ujson.Value =>
        complete(UsersService.open(username))
    }
  }
}
