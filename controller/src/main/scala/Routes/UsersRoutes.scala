import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import upickle_bundle.general._
import scala.util.{Try}

// TODO: move SchemaValidation into its own file. Use that as a function within Validation.
// TODO: create a simple markdownIpsum() function that generates markdown probabalistically using the following randomised components: numberOfParagraphs(1-2), numberOfSentences(1-3), hasHeading. Potentially add some randomised bold and italic words to the paragraphs.
// TODO: surround all DAO calls in a try {} block with a catch like in https://tpolecat.github.io/doobie/docs/09-Error-Handling.html

// TODO: uninstall the angular admin theme package and then reinstall so that you have the latest version.

object UsersRoutes {
  private final def rootRoutes(): Route = concat(
    get(
      Validation("list-users") { body: ujson.Value =>
        {
          val pageNumber: Int = body("page_number").num.toInt
          val pageLength: Int = body("page_length").num.toInt

          complete(UsersServices.list(pageNumber, pageLength))
        }
      }
    ),
    post(
      Validation("create-user") { body: ujson.Value =>
        val username: String     = body("username").str
        val password: String     = body("password").str
        val emailAddress: String = body("email_address").str
        val notes: String        = Try(body("notes").str).getOrElse("")

        complete(UsersServices.create(username, password, emailAddress, notes))
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

  private final def usernameRoutes(): Route = pathPrefix(Segment) {
    username: String =>
      concat(
        get(
          Validation("open-user") { body: ujson.Value =>
            complete(UsersServices.open(username))
          }
        ),
        patch(
          Validation("edit-user") { body: ujson.Value =>
            {
              val newUsername: Option[String] =
                Try(body("username").str).toOption
              val password: Option[String] = Try(body("password").str).toOption
              val emailAddress: Option[String] =
                Try(body("email_address").str).toOption
              val notes: Option[String] = Try(body("notes").str).toOption

              complete(
                UsersServices.edit(
                  username,
                  newUsername,
                  password,
                  emailAddress,
                  notes
                )
              )
            }
          }
        )
      )
  }

  final def apply(): Route = concat(
    usernameRoutes(),
    rootRoutes()
  )
}
