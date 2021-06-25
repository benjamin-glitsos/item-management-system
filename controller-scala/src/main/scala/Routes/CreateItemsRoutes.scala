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
      val acquisitionDate: String     = body("acquisition_date").str
      val expirationDate: Option[String] =
        Try(body("expiration_date").str).toOption
      val unitCost: String          = body("unit_cost").str
      val unitPrice: Option[String] = Try(body("unit_price").str).toOption
      val quantityAvailable: Int    = body("quantity_available").num
      val quantitySold: Int         = body("quantity_sold").num
      val additionalNotes: Option[String] =
        Try(body("additional_notes").str).toOption

      complete(
        NoContent,
        ItemsService.create(
          sku,
          upc,
          name,
          description,
          acquisitionDate,
          expirationDate,
          unitCost,
          unitPrice,
          quantityAvailable,
          quantitySold,
          additionalNotes
        )
      )
    }
  }
}
