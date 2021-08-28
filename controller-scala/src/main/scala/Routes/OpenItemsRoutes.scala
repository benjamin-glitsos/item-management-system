import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object OpenItemsRoutes extends UpickleMixin {
  final def apply(sku: String): Route = get {
    (SetActionKeyMiddleware("open-items") & ValidationMiddleware()) {
      body: ujson.Value =>
        complete(ItemsService.open(sku))
    }
  }
}
