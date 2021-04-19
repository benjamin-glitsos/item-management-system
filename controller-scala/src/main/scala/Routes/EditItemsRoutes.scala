import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import upickle.default._
import upickle_import.general._

object EditItemsRoutes {
  final def apply(key: String): Route = patch {
    ValidationMiddleware("edit-items") { body: ujson.Value =>
      {
        val newKey: Option[String] =
          Try(body("key").str).toOption
        val name: Option[String] =
          Try(body("name").str).toOption
        val description: Option[String] =
          Try(body("description").str).toOption
        val additionalNotes: Option[String] =
          Try(body("additional_notes").str).toOption

        complete(
          ItemsService.edit(
            key,
            newKey,
            name,
            description,
            additionalNotes
          )
        )
      }
    }
  }
}
