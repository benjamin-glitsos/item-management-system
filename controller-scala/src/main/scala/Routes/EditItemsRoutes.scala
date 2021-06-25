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
        val acquisition_date: Option[String] =
          Try(body("acquisition_date").str).toOption
        val expiration_date: Option[String] =
          Try(body("expiration_date").str).toOption
        val unit_cost: Option[String] =
          Try(body("unit_cost").str).toOption
        val unit_price: Option[String] =
          Try(body("unit_price").str).toOption
        val quantity_available: Option[Int] =
          Try(body("quantity_available").num).toOption
        val quantity_sold: Option[Int] =
          Try(body("quantity_sold").num).toOption
        val additionalNotes: Option[Option[String]] =
          Try(body("additional_notes").strOpt).toOption

        complete(
          ItemsService.edit(
            sku,
            newSku,
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
}
