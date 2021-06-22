import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object ItemsRoutes {
  private final def rootRoutes(): Route = concat(
    ListItemsRoutes(),
    CreateItemsRoutes(),
    DeleteItemsRoutes()
  )

  private final def skuRoutes(): Route = pathPrefix(Segment) { sku: String =>
    {
      val skuToUpperCase = sku.toUpperCase()
      concat(
        OpenItemsRoutes(skuToUpperCase),
        EditItemsRoutes(skuToUpperCase)
      )
    }
  }

  final def apply(): Route = concat(
    skuRoutes(),
    rootRoutes()
  )
}
