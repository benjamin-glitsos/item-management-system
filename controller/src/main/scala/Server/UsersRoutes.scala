import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

// TODO: write the test cases after you set up your deployment pipeline so you make sure you can integrate the testing into that
// TODO: remove the redundancy in the complete HttpEntity directives. Merge them into a single CompleteJson directive?
// TODO: move the properties from delete-users.json to definitions/deletion.json. Name them 'method' and 'keysOfItems'
// TODO: optionally, make all of the error handlers use json by using a directive on the ApiRoutes: { errors: [{ code: "example_error", message: "Example error message." }] }

object UsersRoutes {
  def apply(): Route =
    concat(
      pathPrefix(Segment) { username: String =>
        concat(
          get(
            complete(
              HttpEntity(
                ContentTypes.`application/json`,
                UsersServices.open(username)
              )
            )
          )
        )
      },
      get(
        SchemaValidate("list-users") { validatedJson: String =>
          complete(
            HttpEntity(
              ContentTypes.`application/json`,
              UsersServices.list(validatedJson)
            )
          )
        }
      ),
      delete(
        SchemaValidate("delete-users") { validatedJson: String =>
          complete(
            UsersServices.delete(validatedJson)
          )
        }
      )
    )
}
