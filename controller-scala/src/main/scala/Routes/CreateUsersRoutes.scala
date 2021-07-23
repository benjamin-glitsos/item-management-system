import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import akka.http.scaladsl.model.StatusCodes.NoContent

object CreateUsersRoutes {
  final def apply(): Route = post {
    (SetActionKeyMiddleware("create-users") & ValidationMiddleware()) {
      body: ujson.Value =>
        val username: String     = body("username").str
        val emailAddress: String = body("email_address").str
        val firstName: String    = body("first_name").str
        val lastName: String     = body("last_name").str
        val otherNames: Option[String] =
          Try(body("other_names").str).toOption
        val password: String = body("password").str
        val additionalNotes: Option[String] =
          Try(body("additional_notes").str).toOption

        complete(
          NoContent,
          UsersService.create(
            username,
            emailAddress,
            firstName,
            lastName,
            otherNames,
            password,
            additionalNotes
          )
        )
    }
  }
}
