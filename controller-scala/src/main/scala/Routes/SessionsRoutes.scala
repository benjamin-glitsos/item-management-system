import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object SessionsRoutes {
  private final def rootRoutes(): Route = concat(
    LoginSessionsRoutes()
  )

  private final def sessionTokenRoutes(): Route = pathPrefix(Segment) {
    sessionToken: String =>
      concat(
        LogoutSessionsRoutes(sessionToken)
      )
  }

  final def apply(): Route = concat(
    sessionTokenRoutes(),
    rootRoutes()
  )
}
