import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

// TODO: don't worry about error handling, async or test cases. Don't do non-functional things until later when you have more time. Just finish JSON validation then do the other 3 UsersRoutes then you will be onto front-end.

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
