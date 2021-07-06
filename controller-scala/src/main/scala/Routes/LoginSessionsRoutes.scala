import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import akka.http.scaladsl.model.StatusCodes.NoContent

object LoginSessionsRoutes {
  final def apply(): Route = post {
    ValidationMiddleware("login-sessions") { body: ujson.Value =>
      val username: String = body("username").str
      val password: String = body("password").str

      complete(
        // NoContent,
        SessionsService.login(username, password)
      )
    }
  }
}
