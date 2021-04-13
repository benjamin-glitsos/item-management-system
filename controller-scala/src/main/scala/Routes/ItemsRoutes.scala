import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object ItemsRoutes {
  private final def rootRoutes(): Route = concat(
    ListItemsRoutes(),
    CreateItemsRoutes(),
    DeleteItemsRoutes()
  )

  private final def keyRoutes(): Route = pathPrefix(Segment) { key: String =>
    concat(
      OpenItemsRoutes(key),
      EditItemsRoutes(key)
    )
  }

  final def apply(): Route = concat(
    keyRoutes(),
    rootRoutes()
  )
}
