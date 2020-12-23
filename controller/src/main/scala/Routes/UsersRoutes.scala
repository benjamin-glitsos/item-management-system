import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

// TODO: write the test cases after you set up your deployment pipeline so you make sure you can integrate the testing into that
// TODO: just extract only the ServerShutdown part into  anew file. Then delete the comment headings. You will need to accept a function argument of the bindingFuture (pass in an anonymous function) then execute it. E.g. apply(bindingFutureFun: () => Future[ServerBinding]) = { bindingFutureFun() }
// TODO: put the fields in create-user.json into definitions/fields.json ?
// TODO: remove the redundancy in the complete HttpEntity directives. Merge them into a single CompleteJson directive?
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
      post(
        SchemaValidate("create-user") { validatedJson: String =>
          complete(
            HttpEntity(
              ContentTypes.`application/json`,
              UsersServices.create(validatedJson)
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
