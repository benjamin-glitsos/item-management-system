import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import akka.http.scaladsl.model.StatusCodes.NoContent

object DeleteItemsRoutes {
  final def apply(): Route = delete {
    (SetActionKeyMiddleware("delete-items") & ValidationMiddleware()) {
      body: ujson.Value =>
        {
          val method: String     = body("method").str
          val skus: List[String] = read[List[String]](body("skus"))

          complete(NoContent, ItemsService.delete(method, skus))
        }
    }
  }
}
