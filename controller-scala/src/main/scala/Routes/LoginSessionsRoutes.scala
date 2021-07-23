import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object LoginSessionsRoutes extends UpickleMixin {
  final def apply(): Route = post {
    (SetActionKeyMiddleware("login-sessions") & ValidationMiddleware()) {
      body: ujson.Value =>
        val username: String = body("username").str
        val password: String = body("password").str

        complete(SessionsService.login(username, password))
    }
  }
}
