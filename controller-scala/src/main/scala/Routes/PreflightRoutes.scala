import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object CorsRoutes {
  final def preflight(): Route = options {
    complete(new String)
  }
}
