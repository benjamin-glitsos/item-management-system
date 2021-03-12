import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import upickle_bundle.general._
import scala.util.{Try}
import CustomMethodDirectives._

object UsersRoutes {
  private final def rootRoutes(): Route = concat(
    report(
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
        val emailAddress: String = body("email_address").str
        val firstName: String    = body("first_name").str
        val lastName: String     = body("last_name").str
        val otherNames: Option[String] =
          Try(body("other_names").str).toOption
        val password: String      = body("password").str
        val notes: Option[String] = Try(body("notes").str).toOption

        complete(
          UsersServices.create(
            username,
            emailAddress,
            firstName,
            lastName,
            otherNames,
            password,
            notes
          )
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
              val emailAddress: Option[String] =
                Try(body("email_address").str).toOption
              val firstName: Option[String] =
                Try(body("first_name").str).toOption
              val lastName: Option[String] =
                Try(body("last_name").str).toOption
              val otherNames: Option[String] =
                Try(body("other_names").str).toOption
              val password: Option[String] = Try(body("password").str).toOption
              val notes: Option[String]    = Try(body("notes").str).toOption

              complete(
                UsersServices.edit(
                  username,
                  newUsername,
                  emailAddress,
                  firstName,
                  lastName,
                  otherNames,
                  password,
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
