import java.util.Date
import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait ItemsEditService
    extends ServiceMixin
    with DoobieConnectionMixin
    with UpickleMixin {
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
          data <- ItemsDAO
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

          val output: String = createDataOutput(writeJs(data))

        } yield (output)).transact(transactor).unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
