import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object UsersRoutes {
  def apply(): Route = concat(
      get(complete(UsersDAO.list(offset = 0, length = 25)))

      // post(complete("Posted")),
      // delete(complete("Deleted"))
  )
}
