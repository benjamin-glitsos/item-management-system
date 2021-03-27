import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_bundle.general._
import CustomMethodDirectives.report
import scala.util.{Try}

object ListUsersRoutes {
  final def apply(): Route = report {
    Validation("list-users") { body: ujson.Value =>
      {
        val pageNumber: Int        = body("page_number").num.toInt
        val pageLength: Int        = body("page_length").num.toInt
        val search: Option[String] = Try(body("search").str).toOption

        complete(UsersServices.list(pageNumber, pageLength, search))
      }
    }
  }
}
