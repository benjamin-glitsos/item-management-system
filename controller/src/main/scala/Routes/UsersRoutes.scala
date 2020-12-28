import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import upickle_bundle.implicits._

// TODO: uninstall the angular admin theme package and then reinstall so that you have the latest version.
//
// TODO: create a markdownIpsum() function that generates markdown probabalistically using the following randomised components: numberOfParagraphs, numberOfSentences, hasHeading, hasSubheading, italicSentence, boldSentence, paragraphType (normal, blockquote, list).

// TODO: If you can't detect psql errors and handle them in a better way (using just the one API call), create an apply method in DAOCheckUnique which takes (tableName, columnName, value) and returns true or false for if the value is unique or not.

// TODO: put password validation handling inside UsersRoutes. Then make the generatePassword method return a valid password every time.

// TODO: make formatErrorJson return a ujson.Value array of errors, and then make the handler wrap it in { errors: [] }.
// TODO: formatErrorJson code could be improved with implicits to handle Error type and also NonEmptyList type
// TODO: test that multiple validation errors get accumulated into a list.

// TODO: look for any flatMap or map that can be converted to >>= or <*> or any other Haskell symbols
// TODO: add 'final' keyword to most methods?

// TODO: Error Handling
// https://blog.ssanj.net/posts/2019-08-18-using-validated-for-error-accumulation-in-scala-with-cats.html
// Validation is just a single directive, not split into parts. It uses Validated internally but returns an unwrapped ujson value. The directive combines ContentType and Schema validation into valid or invalid. Then custom error handling is done within the Service. Demonstrate this by doing password validation within the Service. At the end of the directive, the Validated is matched to either pass(ujson) or reject(NonEmptyList) however you will need to write a function that converts NonEmptyList to stringified json for the reject to use. UsersRoutes uses Validated internally as well for its custom error handling.
//
// OLD: TODO: Error Handling
// OLD: I think the solution is that Validation will be a directive that composes other directives. Each directive returns ujson.Value but uses Validated internally. Composing the Validated errors is very problematic and probably illogical. The first directive can just convert the body to ujson.Value and throw error of invalid json if that doesnt work. TODO: then make a scala implicit conversion for NonEmptyList[Error] to ValidationRejection
// OLD:
// OLD: TODO: Error Handling
// OLD: * Change the marshaller so that it handles Validated[ujson.Value] types. It provides or rejects them. It writes the value to a string, and also the errors list to a json object then to a string.
// OLD: * Extract the SchemaValidation into a separate object that returns Validated[ujson.Value]. Then the Validation object will sequence and aggregate the separate validators.
// OLD: But then how do you get the values out of the Validated data type without it being messy. Need to think about this.
// OLD:
// OLD: TODO: Error Handling
// OLD: ## Refer to: ##
// OLD: https://www.gregbeech.com/2018/08/12/akka-http-entity-validation/
// OLD: https://doc.akka.io/docs/akka-http/current/routing-dsl/directives/marshalling-directives/entity.html
// OLD: ## Plan: ##
// OLD: * The ApiRoutes will be wrapped in a handleExceptions directive which takes an Invalid[Error] data type and converts it to an error containing JsValue: { errors: [{ code: "example_error", message: "Example error message." }] }. The Error case class will be Error(code, message). Hence the routes that you make will return Validated[JsValue, Error] which means Valid[JsValue] and Invalid[Error].
// OLD: * Each endpoint will be wrapped in ValidateRequest(name) instead of SchemaValidate. This takes a unique name such as "open-user" for each endpoint. ValidateRequest first matches name against a list of names which don't require a body and returns Valid(new JsValue) for them immediately. For the rest, it then extracts the request body and optionally the content-type header and goes through the following validation steps which each accumulate the list of validation errors within their own steps (but not of other steps). (Hopefully it is possible to throw the HTTP status code along with the error, as then you can customise it e.g. 409 Duplicate Content or 400 Bad Request.):
// OLD:     * Converts request body to JsValue and optionally, checks if the content-type is application/json, returning Valid[JsValue] using the Cats Validate data type.
// OLD:     NOTE: everit schema doesnt use JsValue. It uses a different AST. Ok that changes this... Now the lingua franca type should be String.
// OLD:     * Uses schema validation (the name is the filename of the schema json file), returning Valid[JsValue].
// OLD:     * Uses custom validations if any are found (the name is matched by a case expression to various custom validation objects), returning Valid[JsValue].
// OLD: (Also note that you may want to extract the values from the json data type and pass them to the services in the UsersRoutes object, rather than inside the individual services. This is just for DRYness so that each individual service doesn't need to handle the conversions.)
// OLD: * Each Service will return Valid[JsValue]. This allows services to throw their own errors in the Invalid case, but no services need this currently. A custom marshaller will be required to implicitly marshall Valid[JsValue] to the response body. Marshalling JsValue to the response body should automatically make the content-type of the response be application/json, so this removes the need for your Complete directive.
// (Tidy up the seeding of the two default users within UsersSeeder after you make the Services accept values rather than a json string)
// (Delete the Directives directory after this.)

object UsersRoutes {
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
      Validation("delete-users") { body: ujson.Value =>
        {
          val method: String          = body("method").str
          val usernames: List[String] = read[List[String]](body("usernames"))

          complete(UsersServices.delete(method, usernames))
        }
      }
    )
  )

  private def usernameRoutes(): Route = pathPrefix(Segment) {
    username: String =>
      concat(
        get(
          Validation("open-user") { body: ujson.Value =>
            complete(UsersServices.open(username))
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
