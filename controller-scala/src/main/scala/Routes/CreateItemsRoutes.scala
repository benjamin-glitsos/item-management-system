import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import akka.http.scaladsl.model.StatusCodes.NoContent

object CreateItemsRoutes {
  final def apply(): Route = post {
    ValidationMiddleware("create-items") { body: ujson.Value =>
      val sku: String                 = body("sku").str
      val upc: String                 = body("upc").str
      val name: String                = body("name").str
      val description: Option[String] = Try(body("description").str).toOption
      val acquisition_date: String    = body("acquisition_date").str
      val expiration_date: String     = body("expiration_date").str
      val unit_cost: String           = body("unit_cost").str
      val unit_price: String          = body("unit_price").str
      val quantity_available: Int     = body("quantity_available").num
      val quantity_sold: Int          = body("quantity_sold").num
      val additionalNotes: Option[String] =
        Try(body("additional_notes").str).toOption

      complete(
        NoContent,
        ItemsService.create(
          sku,
          upc,
          name,
          description,
          acquisition_date,
          expiration_date,
          unit_cost,
          unit_price,
          quantity_available,
          quantity_sold,
          additionalNotes
        )
      )
    }
  }
}
