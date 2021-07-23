import java.util.Date
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.{Try}
import akka.http.scaladsl.model.StatusCodes.NoContent

object CreateItemsRoutes extends DateMixin {
  final def apply(): Route = post {
    (SetActionKeyMiddleware("create-items") & ValidationMiddleware()) {
      body: ujson.Value =>
        val sku: String                 = body("sku").str
        val upc: String                 = body("upc").str
        val name: String                = body("name").str
        val description: Option[String] = Try(body("description").str).toOption
        val acquisitionDate: Date =
          dateParse(body("acquisition_date").str)
        val expirationDate: Option[Date] =
          Try(dateParse(body("expiration_date").str)).toOption
        val unitCost: Double = body("unit_cost").str.toDouble
        val unitPrice: Option[Double] =
          Try(body("unit_price").str.toDouble).toOption
        val quantityAvailable: Int = body("quantity_available").num.toInt
        val quantitySold: Int      = body("quantity_sold").num.toInt
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
