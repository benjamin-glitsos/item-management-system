import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.util.Try
import upickle_import.general._

object EditItemsRoutes {
  final def apply(sku: String): Route = patch {
    ValidationMiddleware("edit-items") { body: ujson.Value =>
      {
        val newSku: Option[String] =
          Try(body("sku").str).toOption
        val upc: Option[String] =
          Try(body("upc").str).toOption
        val name: Option[String] =
          Try(body("name").str).toOption
        val description: Option[Option[String]] =
          Try(body("description").strOpt).toOption
        val acquisitionDate: Option[String] =
          Try(body("acquisition_date").str).toOption
        val expirationDate: Option[Option[String]] =
          Try(body("expiration_date").strOpt).toOption
        val unitCost: Option[String] =
          Try(body("unit_cost").str).toOption
        val unitPrice: Option[Option[String]] =
          Try(body("unit_price").strOpt).toOption
        val quantityAvailable: Option[Int] =
          Try(body("quantity_available").num.toInt).toOption
        val quantitySold: Option[Int] =
          Try(body("quantity_sold").num.toInt).toOption
        val additionalNotes: Option[Option[String]] =
          Try(body("additional_notes").strOpt).toOption

        complete(
          ItemsService.edit(
            sku,
            newSku,
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
}
