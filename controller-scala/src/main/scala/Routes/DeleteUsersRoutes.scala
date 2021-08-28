import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import akka.http.scaladsl.model.StatusCodes.NoContent

object DeleteUsersRoutes {
  final def apply(): Route = delete {
    (SetActionKeyMiddleware("delete-users") & ValidationMiddleware()) {
      body: ujson.Value =>
        {
          val method: String          = body("method").str
          val usernames: List[String] = read[List[String]](body("usernames"))

          complete(NoContent, UsersService.delete(method, usernames))
        }
    }
  }
}
