import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import upickle_bundle.general._
import scala.util.{Try}

// TODO: uninstall the angular admin theme package and then reinstall so that you have the latest version.
//
// TODO: create a markdownIpsum() function that generates markdown probabalistically using the following randomised components: numberOfParagraphs, numberOfSentences, hasHeading, hasSubheading, italicSentence, boldSentence, paragraphType (normal, blockquote, list).

// TODO: If you can't detect psql errors and handle them in a better way (using just the one API call), create an apply method in DAOCheckUnique which takes (tableName, columnName, value) and returns true or false for if the value is unique or not.

// TODO: put password validation handling inside UsersRoutes. Then make the generatePassword method return a valid password every time.

// TODO: look for any flatMap or map that can be converted to >>= or <*> or any other Haskell symbols
// TODO: add 'final' keyword to most methods?
//
// (Delete the Directives directory after this.)

object UsersRoutes {
  private def rootRoutes(): Route = concat(
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
