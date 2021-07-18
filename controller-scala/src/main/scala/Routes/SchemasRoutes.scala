import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object SchemasRoutes extends UpickleMixin {
  final def apply(): Route = pathPrefix(Segment) { name: String =>
    get {
      complete(SchemasService.loadJson(name, isFull = false))
    }
  }
}
