import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait ItemsCreateService extends ServiceTrait {
  final def create(
      sku: String,
      upc: String,
      name: String,
      description: Option[String],
      acquisitionDate: String,
      expirationDate: Option[String],
      unitCost: String,
      unitPrice: Option[String],
      quantityAvailable: Int,
      quantitySold: Int,
      additionalNotes: Option[String]
  ): String = {
    try {
      ItemsDAO
        .create(
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
        .transact(transactor)
        .unsafeRunSync
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }

  }
}
