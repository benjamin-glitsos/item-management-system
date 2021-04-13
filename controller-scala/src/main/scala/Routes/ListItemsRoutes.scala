import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_import.general._
import upickle.default._
import CustomMethodDirectives.report
import scala.util.{Try}

object ListItemsRoutes extends ListTrait {
  final def apply(): Route = report {
    ValidationDirective("list-items") { body: ujson.Value =>
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
