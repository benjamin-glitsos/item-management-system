import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

// TODO: write the test cases after you set up your deployment pipeline so you make sure you can integrate the testing into that
// TODO: consider accepting command-line flags which will override values specified in env file. These flags can be used by your CI/CD pipeline e.g. --testing=true --admin=false --seeding-factor=2. This will need to be overwrite-merged into the env variables at the bash level in the docker container, so maybe you can install a bash tool using the dockerfile that can do this merging and flag handling.
// TODO: tidy the imports by nesting them like this {One, Two} and using specific imports rather than the underscore for all imports.
// TODO: convert the Users List endpoint to doobie sql and then make the columns be dynamic (accepting an array but with a default array defined by the schema json), and also the ordering (accepting a tuple of (column name, asc/desc)), and the where clauses (a list of 3-tuples (column name, operator, data)) and the where clauses defaults to [("is_deleted", "equals", "false")].
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
// (Also note that you may want to extract the values from the json data type and pass them to the services in the UsersRoutes object, rather than inside the individual services. This is just for DRYness so that each individual service doesn't need to handle the conversions.)
// * Each Service will return Valid[JsValue]. This allows services to throw their own errors in the Invalid case, but no services need this currently. A custom marshaller will be required to implicitly marshall Valid[JsValue] to the response body. Marshalling JsValue to the response body should automatically make the content-type of the response be application/json, so this removes the need for your Complete directive.
// (Tidy up the seeding of the two default users within UsersSeeder after you make the Services accept values rather than a json string)

object UsersRoutes {
  private def rootRoutes(): Route = concat(
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

  private def usernameRoutes(): Route = pathPrefix(Segment) {
    username: String =>
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
  }

  def apply(): Route = concat(
    usernameRoutes(),
    rootRoutes()
  )
}
