import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

// TODO: don't worry about error handling, async or test cases. Don't do non-functional things until later when you have more time. Just finish JSON validation then do the other 3 UsersRoutes then you will be onto front-end.

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
// get & path(emailAddress)
// post(complete("Posted")),
// delete(complete("Deleted"))
