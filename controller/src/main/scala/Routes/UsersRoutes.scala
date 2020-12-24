import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

// TODO: write the test cases after you set up your deployment pipeline so you make sure you can integrate the testing into that
// TODO: also, do the seeding after deployment as well so that you know how to structure the code
// TODO: continue adding type signatures to all values and methods, including implicit values. Needed on:
// - Server.scala
// - SchemaValidate.scala
// - Complete.scala
// - DoobieBundle.scala
// - All DAO objects (need annotation after their method definition)
// TODO: pull the pathPrefix into the usernameRoutes() and also create a private def routes() so that apply() will just contain the two function calls inside its concat.
// TODO: add an is_deleted column (to the right of 'edits') and use that as the source of truth for the query filters and trigger to use. It should solve the issue with the restore trigger allowing the user to restore a file that wasn't deleted.
//
// !!!!!!!
//
// TODO: instead of SchemaValidate directive, use 'entity as' to unmarshal Valid[Json] and this will handle the errors as well, and it should also remove the need for Complete.json. Therefore, each Service will return a Validated[Json]. The schema validation function will be called from the Service, plus any other validations e.g. duplicate content. (Unsolved problem: how to return the correct HTTP status code - 409 Duplicate Content or 400 Bad Request.) See:
// https://www.gregbeech.com/2018/08/12/akka-http-entity-validation/
// https://doc.akka.io/docs/akka-http/current/routing-dsl/directives/marshalling-directives/entity.html
//
// !!!!!!!
//
// TODO: optionally, make all of the error handlers use json by using a directive on the ApiRoutes: { errors: [{ code: "example_error", message: "Example error message." }] }. Use a case class for Error(code, message)

object UsersRoutes {
  private def usernameRoutes(username: String): Route = concat(
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

  def apply(): Route = concat(
    pathPrefix(Segment) { username: String => usernameRoutes(username) },
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
