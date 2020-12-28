import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import upickle_bundle.general._

// TODO: uninstall the angular admin theme package and then reinstall so that you have the latest version.
//
// TODO: create a markdownIpsum() function that generates markdown probabalistically using the following randomised components: numberOfParagraphs, numberOfSentences, hasHeading, hasSubheading, italicSentence, boldSentence, paragraphType (normal, blockquote, list).

// TODO: If you can't detect psql errors and handle them in a better way (using just the one API call), create an apply method in DAOCheckUnique which takes (tableName, columnName, value) and returns true or false for if the value is unique or not.

// TODO: put password validation handling inside UsersRoutes. Then make the generatePassword method return a valid password every time.

// TODO: look for any flatMap or map that can be converted to >>= or <*> or any other Haskell symbols
// TODO: add 'final' keyword to most methods?
//
// (Tidy up the seeding of the two default users within UsersSeeder after you make the Services accept values rather than a json string)
// (Delete the Directives directory after this.)

object UsersRoutes {
  private def rootRoutes(): Route = concat(
    get(
      SchemaValidate("list-users") { validatedBody: String =>
        {
          val body: ujson.Value = read[ujson.Value](validatedBody)
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
