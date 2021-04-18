import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ItemsOpenService extends ServiceTrait {
  final def open(key: String): ujson.Value = {
    val keyToUpperCase = key.toUpperCase()
    read[ujson.Value](
      try {
        (for {
          _ <- ItemsDAO.incrementOpens(keyToUpperCase)

          data <- ItemsDAO.open(keyToUpperCase)

          val output: String = createDataOutput(writeJs(data))

        } yield (output))
          .transact(xa)
          .unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
