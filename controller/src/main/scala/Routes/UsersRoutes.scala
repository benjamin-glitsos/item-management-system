import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

// TODO: write the test cases after you set up your deployment pipeline so you make sure you can integrate the testing into that
// TODO: optionally, make all of the error handlers use json by using a directive on the ApiRoutes: { errors: [{ code: "example_error", message: "Example error message." }] }

object UsersRoutes {
  def apply(): Route =
    concat(
      pathPrefix(Segment) { username: String =>
        concat(
          get(
            Complete.json(UsersServices.open(username))
          ),
          patch(
            SchemaValidate("edit-user") { validatedJson: String =>
              Complete.text(
                UsersServices.edit(username, validatedJson)
              )
            }
          )
        )
      },
      get(
        SchemaValidate("list-users") { validatedJson: String =>
          Complete.json(UsersServices.list(validatedJson))
        }
      ),
      post(
        SchemaValidate("create-user") { validatedJson: String =>
          Complete.text(
            UsersServices.create(validatedJson)
          )
        }
      ),
      delete(
        SchemaValidate("delete-users") { validatedJson: String =>
          Complete.text(
            UsersServices.delete(validatedJson)
          )
        }
      )
    )
}
