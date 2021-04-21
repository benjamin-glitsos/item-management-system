import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.Try
import upickle_import.general._

object EditItemsRoutes {
  final def apply(key: String): Route = patch {
    ValidationMiddleware("edit-items") { body: ujson.Value =>
      {
        val newKey: Option[String] =
          Try(body("key").str).toOption
        val name: Option[String] =
          Try(body("name").str).toOption
        val description: Option[Option[String]] =
          Try(body("description").strOpt).toOption
        val additionalNotes: Option[Option[String]] =
          Try(body("additional_notes").strOpt).toOption

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
