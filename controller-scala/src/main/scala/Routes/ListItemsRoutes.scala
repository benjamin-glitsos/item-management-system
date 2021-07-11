import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import CustomMethodMiddlewares.report
import scala.util.{Try}

object ListItemsRoutes extends ListMixin with UpickleMixin {
  final def apply(): Route = report {
    ValidationMiddleware("list-items") { body: ujson.Value =>
      {
        val pageNumber: Int        = body("page_number").num.toInt
        val pageLength: Int        = body("page_length").num.toInt
        val search: Option[String] = Try(body("search").str).toOption
        val sort: Sort             = read[Sort](body("sort"))

        complete(ItemsService.list(pageNumber, pageLength, search, sort))
      }
    }
  }
}
