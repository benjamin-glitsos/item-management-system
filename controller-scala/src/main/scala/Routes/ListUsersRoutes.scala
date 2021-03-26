import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_bundle.general._
import CustomMethodDirectives.report

object ListUsersRoutes {
  final def apply(): Route = report {
    Validation("list-users") { body: ujson.Value =>
      {
        val pageNumber: Int = body("page_number").num.toInt
        val pageLength: Int = body("page_length").num.toInt
        val search: String  = body("search").str

        complete(UsersServices.list(pageNumber, pageLength, search))
      }
    }
  }
}
