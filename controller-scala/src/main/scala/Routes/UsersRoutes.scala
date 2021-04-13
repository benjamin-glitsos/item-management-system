import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object UsersRoutes {
  private final def rootRoutes(): Route = concat(
    ListUsersRoutes(),
    CreateUsersRoutes(),
    DeleteUsersRoutes()
  )

  private final def usernameRoutes(): Route = pathPrefix(Segment) {
    username: String =>
      concat(
        OpenUsersRoutes(username),
        EditUsersRoutes(username)
      )
  }

  final def apply(): Route = concat(
    usernameRoutes(),
    rootRoutes()
  )
}
