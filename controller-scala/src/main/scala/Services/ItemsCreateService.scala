import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait ItemsCreateService extends ServiceTrait {
  final def create(
      sku: String,
      upc: String,
      name: String,
      description: Option[String],
      acquisition_date: String,
      expiration_date: Option[String],
      unit_cost: String,
      unit_price: Option[String],
      quantity_available: Int,
      quantity_sold: Int,
      additionalNotes: Option[String]
  ): String = {
    try {
      ItemsDAO
        .create(
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
        .transact(transactor)
        .unsafeRunSync
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }

  }
}
