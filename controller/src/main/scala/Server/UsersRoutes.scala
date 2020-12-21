import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

// TODO: the users list service will need to return the 'total_pages' and 'total_results' and 'range_start' and 'range_end' and hence will need to nest the results within 'data'. The total_pages is needed for the front-end.
// TODO: don't worry about error handling, async or test cases. Don't do non-functional things until later when you have more time. Just finish JSON validation then do the other 3 UsersRoutes then you will be onto front-end.

object UsersRoutes {
  def apply(): Route =
    concat(
        get(
            SchemaValidate("list-users") { validatedBody =>
              complete(
                  HttpEntity(
                      ContentTypes.`application/json`,
                      UsersServices.list(validatedBody)
                  )
              )
            }
        )
    )
}
// get & path(emailAddress)
// post(complete("Posted")),
// delete(complete("Deleted"))
