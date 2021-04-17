import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import akka.http.scaladsl.model.StatusCodes.NoContent
import upickle.default._
import upickle_import.general._

object EditUsersRoutes {
  final def apply(username: String): Route = patch {
    ValidationDirective("edit-users") { body: ujson.Value =>
      {
        val newUsername: Option[String] =
          Try(body("username").str).toOption
        val firstName: Option[String] =
          Try(body("first_name").str).toOption
        val lastName: Option[String] =
          Try(body("last_name").str).toOption
        val otherNames: Option[Option[String]] =
          Try(body("other_names").strOpt).toOption
        val emailAddress: Option[String] =
          Try(body("email_address").str).toOption
        val password: Option[String] = Try(body("password").str).toOption
        val additionalNotes: Option[Option[String]] =
          Try(body("additional_notes").strOpt).toOption

        complete(
          NoContent,
          UsersService.edit(
            username,
            newUsername,
            firstName,
            lastName,
            otherNames,
            emailAddress,
            password,
            additionalNotes
          )
        )
      }
    }
  }
}
