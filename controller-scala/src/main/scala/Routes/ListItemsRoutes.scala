import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import scala.util.{Try}

object ListItemsRoutes
    extends ListMixin
    with UpickleMixin
    with HttpMethodsMixin {
  final def apply(): Route = report {
    (SetActionKeyMiddleware("list-items") & ValidationMiddleware()) {
      body: ujson.Value =>
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
