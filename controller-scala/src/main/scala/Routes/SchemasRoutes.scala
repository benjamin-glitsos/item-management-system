import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_import.general._

object SchemasRoutes {
  final def apply(): Route = pathPrefix(Segment) { name: String =>
    get {
      complete(SchemasService.loadJson(name, isFull = false))
    }
  }
}
