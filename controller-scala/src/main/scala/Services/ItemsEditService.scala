import java.util.Date
import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ItemsEditService extends ServiceTrait {
  final def edit(
      oldSku: String,
      newSku: Option[String],
      upc: Option[String],
      name: Option[String],
      description: Option[Option[String]],
      acquisitionDate: Option[Date],
      expirationDate: Option[Option[Date]],
      unitCost: Option[Double],
      unitPrice: Option[Option[Double]],
      quantityAvailable: Option[Int],
      quantitySold: Option[Int],
      additionalNotes: Option[Option[String]]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- ItemsDAO
            .edit(
              oldSku,
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

          data <- ItemsDAO.open(newSku.getOrElse(oldSku))

          val output: String = createDataOutput(writeJs(data))

        } yield (output))
          .transact(transactor)
          .unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
