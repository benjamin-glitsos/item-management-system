import java.util.Date
import java.sql.SQLException
import doobie.implicits._

trait ItemsCreateService extends ServiceMixin with DoobieConnectionMixin {
  final def create(
      sku: String,
      upc: String,
      name: String,
      description: Option[String],
      acquisitionDate: Date,
      expirationDate: Option[Date],
      unitCost: Double,
      unitPrice: Option[Double],
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
