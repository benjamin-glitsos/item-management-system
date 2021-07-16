import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object LogoutSessionsRoutes extends UpickleMixin {
  final def apply(): Route = delete {
    (ValidationMiddleware("logout-sessions") & cachingProhibited) {
      body: ujson.Value =>
        val authenticationToken: String = body("authentication_token").str

        complete(SessionsService.logout(authenticationToken))
    }
  }
}
