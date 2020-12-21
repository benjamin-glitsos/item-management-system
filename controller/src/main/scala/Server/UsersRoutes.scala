import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

// TODO: rename the file and object names of the routes e.g. ApiRoutes => VersionRoutes
// TODO: make the offset, page_length better. Maybe use page_number and page_length? Then write the description and title content in the json schema.
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
