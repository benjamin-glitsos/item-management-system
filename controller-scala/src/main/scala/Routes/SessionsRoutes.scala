import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object SessionsRoutes {
  final def apply(): Route = concat(
    LoginSessionsRoutes(),
    LogoutSessionsRoutes()
  )
}
