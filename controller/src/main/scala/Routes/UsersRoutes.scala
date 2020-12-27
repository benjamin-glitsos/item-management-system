import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._

// TODO: Error Handling
// ## Refer to: ##
// https://www.gregbeech.com/2018/08/12/akka-http-entity-validation/
// https://doc.akka.io/docs/akka-http/current/routing-dsl/directives/marshalling-directives/entity.html
// ## Plan: ##
// * The ApiRoutes will be wrapped in a handleExceptions directive which takes an Invalid[Error] data type and converts it to an error containing JsValue: { errors: [{ code: "example_error", message: "Example error message." }] }. The Error case class will be Error(code, message). Hence the routes that you make will return Validated[JsValue, Error] which means Valid[JsValue] and Invalid[Error].
// * Each endpoint will be wrapped in ValidateRequest(name) instead of SchemaValidate. This takes a unique name such as "open-user" for each endpoint. ValidateRequest first matches name against a list of names which don't require a body and returns Valid(new JsValue) for them immediately. For the rest, it then extracts the request body and optionally the content-type header and goes through the following validation steps which each accumulate the list of validation errors within their own steps (but not of other steps). (Hopefully it is possible to throw the HTTP status code along with the error, as then you can customise it e.g. 409 Duplicate Content or 400 Bad Request.):
//     * Converts request body to JsValue and optionally, checks if the content-type is application/json, returning Valid[JsValue] using the Cats Validate data type.
//     NOTE: everit schema doesnt use JsValue. It uses a different AST. Ok that changes this... Now the lingua franca type should be String.
//     * Uses schema validation (the name is the filename of the schema json file), returning Valid[JsValue].
//     * Uses custom validations if any are found (the name is matched by a case expression to various custom validation objects), returning Valid[JsValue].
// (Also note that you may want to extract the values from the json data type and pass them to the services in the UsersRoutes object, rather than inside the individual services. This is just for DRYness so that each individual service doesn't need to handle the conversions.)
// * Each Service will return Valid[JsValue]. This allows services to throw their own errors in the Invalid case, but no services need this currently. A custom marshaller will be required to implicitly marshall Valid[JsValue] to the response body. Marshalling JsValue to the response body should automatically make the content-type of the response be application/json, so this removes the need for your Complete directive.
// (Tidy up the seeding of the two default users within UsersSeeder after you make the Services accept values rather than a json string)
// (Delete the Directives directory after this.)

object UsersRoutes extends ValidationTrait {
  private def rootRoutes(): Route = concat(
    get(
      SchemaValidate("list-users") { validatedBody: String =>
        {
          val body: ujson.Value = ujson.read(validatedBody)
          val pageNumber: Int   = body("page_number").num.toInt
          val pageLength: Int   = body("page_length").num.toInt

          Complete.json(UsersServices.list(pageNumber, pageLength))
        }
      }
    ),
    post(
      SchemaValidate("create-user") { validatedBody: String =>
        Complete.text(
          UsersServices.create(validatedBody)
        )
      }
    ),
    delete(
      SchemaValidate("delete-users") { validatedBody: String =>
        {
          val body: ujson.Value       = ujson.read(validatedBody)
          val method: String          = body("method").str
          val usernames: List[String] = read[List[String]](body("usernames"))

          Complete.text(
            UsersServices.delete(method, usernames)
          )
        }
      }
    )
  )

  private def usernameRoutes(): Route = pathPrefix(Segment) {
    username: String =>
      concat(
        get(
          Validation("open-user") { validatedBody: Validated[ujson.Value] =>
            Complete.json(UsersServices.open(username))
          }
        ),
        patch(
          SchemaValidate("edit-user") { validatedBody: String =>
            Complete.text(
              UsersServices.edit(username, validatedBody)
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
