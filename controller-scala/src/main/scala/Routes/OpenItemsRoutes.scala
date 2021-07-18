import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object OpenItemsRoutes extends UpickleMixin {
  final def apply(sku: String): Route = get {
    ValidationMiddleware("open-items") { body: ujson.Value =>
      complete(ItemsService.open(sku))
    }
  }
}
