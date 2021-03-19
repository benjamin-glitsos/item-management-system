import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._

object DeleteUsersRoutes {
  final def apply(): Route = delete {
    Validation("delete-users") { body: ujson.Value =>
      {
        val method: String          = body("method").str
        val usernames: List[String] = read[List[String]](body("usernames"))

        complete(UsersServices.delete(method, usernames))
      }
    }
  }
}
