import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_bundle.general._

object OpenItemRoutes {
  final def apply(key: String): Route = get {
    Validation("open-item") { body: ujson.Value =>
      complete(ItemsService.open(key))
    }
  }
}
