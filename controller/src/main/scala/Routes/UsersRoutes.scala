import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

// TODO: write the test cases after you set up your deployment pipeline so you make sure you can integrate the testing into that
// TODO: also, do the seeding after deployment as well so that you know how to structure the code. The Seeder() will be put in Main.scala above the Server() call. The Seeder object will reference other individual seeder objects but will call them a number of repeated times e.g. UsersWithMetaSeeder.repeat(absolute(15 * Sys.getEnvOrElse("SEEDING_FACTOR", 1))). The SEEDING_FACTOR is an env variable that can be used to multiply the number that gets seeded by more or less.
// TODO: continue adding type signatures to all values and methods, including implicit values. Needed on:
// - Server.scala
// - SchemaValidate.scala
// - Complete.scala
// - DoobieBundle.scala
// - All DAO objects (need annotation after their method definition)
// TODO: pull the pathPrefix into the usernameRoutes() and also create a private def routes() so that apply() will just contain the two function calls inside its concat.
// TODO: add an is_deleted column (to the right of 'edits') and use that as the source of truth for the query filters and trigger to use. It should solve the issue with the restore trigger allowing the user to restore a file that wasn't deleted.
// TODO: consider accepting command-line flags which will override values specified in env file. These flags can be used by your CI/CD pipeline e.g. --testing=true --admin=false --seeding-factor=2. This will need to be overwrite-merged into the env variables at the bash level in the docker container, so maybe you can install a bash tool using the dockerfile that can do this merging and flag handling.
//
// TODO: Error Handling
// ## Refer to: ##
// https://www.gregbeech.com/2018/08/12/akka-http-entity-validation/
// https://doc.akka.io/docs/akka-http/current/routing-dsl/directives/marshalling-directives/entity.html
// ## Plan: ##
// * The ApiRoutes will be wrapped in a handleExceptions directive which takes an Invalid[Error] data type and converts it to an error containing JsValue: { errors: [{ code: "example_error", message: "Example error message." }] }. The Error case class will be Error(code, message). Hence the routes that you make will return Validated[JsValue, Error] which means Valid[JsValue] and Invalid[Error].
// * Each endpoint will be wrapped in ValidateRequest(name) instead of SchemaValidate. This takes a unique name such as "open-user" for each endpoint. ValidateRequest first matches name against a list of names which don't require a body and returns Valid(new JsValue) for them immediately. For the rest, it then extracts the request body and optionally the content-type header and goes through the following validation steps which each accumulate the list of validation errors within their own steps (but not of other steps). (Hopefully it is possible to throw the HTTP status code along with the error, as then you can customise it e.g. 409 Duplicate Content or 400 Bad Request.):
//     * Converts request body to JsValue and optionally, checks if the content-type is application/json, returning Valid[JsValue] using the Cats Validate data type.
//     * Uses schema validation (the name is the filename of the schema json file), returning Valid[JsValue].
//     * Uses custom validations if any are found (the name is matched by a case expression to various custom validation objects), returning Valid[JsValue].
// * Each Service will return Valid[JsValue]. This allows services to throw their own errors in the Invalid case, but no services need this currently. A custom marshaller will be required to implicitly marshall Valid[JsValue] to the response body. Marshalling JsValue to the response body should automatically make the content-type of the response be application/json, so this removes the need for your Complete directive.

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
