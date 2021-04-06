import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import akka.http.scaladsl.model.StatusCodes.NoContent

object CreateItemsRoutes {
  final def apply(): Route = post {
    Validation("create-item") { body: ujson.Value =>
      val key: String                 = body("key").str
      val name: String                = body("name").str
      val description: Option[String] = Try(body("description").str).toOption
      val notes: Option[String]       = Try(body("notes").str).toOption

      complete(
        NoContent,
        ItemsService.create(
          key,
          name,
          description,
          notes
        )
      )
    }
  }
}
