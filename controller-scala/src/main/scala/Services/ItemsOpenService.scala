import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait ItemsOpenService
    extends ServiceTrait
    with DoobieConnectionTrait
    with UpickleTrait {
  final def open(sku: String): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- ItemsDAO.incrementOpens(sku)

          data <- ItemsDAO.open(sku)

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
