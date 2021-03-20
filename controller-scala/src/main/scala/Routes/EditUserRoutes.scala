import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}

  object EditUserRoutes {
  final def apply(username: String): Route = patch {
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
  }
}
