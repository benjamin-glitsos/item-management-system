import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

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
