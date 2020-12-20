import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

// TODO: reject requests that dont have application/json header
// TODO: make this route/UsersService use the request JSON and validate it using the everit JSON Schema package.
// TODO: make custom error response which is the same but using JSON. E.g. { errors: ["The requested object was not ..."] }
// TODO: make sure this is async and nothing is blocking

object UsersRoutes {
  def apply(entity: String): Route =
    concat(
        get(
            complete(
                HttpEntity(
                    ContentTypes.`application/json`,
                    UsersServices.list(entity)
                )
            )
        )
    )
}
// post(complete("Posted")),
// delete(complete("Deleted"))
