import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import akka.http.scaladsl.model.StatusCodes.NoContent

object DeleteItemsRoutes {
  final def apply(): Route = delete {
    ValidationMiddleware("delete-items") { body: ujson.Value =>
      {
        val method: String     = body("method").str
        val keys: List[String] = read[List[String]](body("keys"))

        complete(NoContent, ItemsService.delete(method, keys))
      }
    }
  }
}
