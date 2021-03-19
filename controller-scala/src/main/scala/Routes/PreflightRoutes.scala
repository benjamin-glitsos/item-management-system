import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object PreflightRoutes {
  final def apply(): Route = options {
    complete(new String)
  }
}
