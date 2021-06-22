import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import akka.http.scaladsl.model.StatusCodes.NoContent

object CreateItemsRoutes {
  final def apply(): Route = post {
    ValidationMiddleware("create-items") { body: ujson.Value =>
      val sku: String                 = body("sku").str
      val name: String                = body("name").str
      val description: Option[String] = Try(body("description").str).toOption
      val additionalNotes: Option[String] =
        Try(body("additional_notes").str).toOption

      complete(
        NoContent,
        ItemsService.create(
          sku,
          name,
          description,
          additionalNotes
        )
      )
    }
  }
}
