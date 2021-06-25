import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ItemsEditService extends ServiceTrait {
  final def edit(
      oldSku: String,
      newSku: Option[String],
      upc: String,
      name: Option[String],
      description: Option[Option[String]],
      acquisition_date: String,
      expiration_date: String,
      unit_cost: String,
      unit_price: String,
      quantity_available: Int,
      quantity_sold: Int,
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
              acquisition_date,
              expiration_date,
              unit_cost,
              unit_price,
              quantity_available,
              quantity_sold,
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
