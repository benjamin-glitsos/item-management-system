import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_import.general._

object OpenItemsRoutes {
  final def apply(sku: String): Route = get {
    ValidationMiddleware("open-items") { body: ujson.Value =>
      complete(ItemsService.open(sku))
    }
  }
}
