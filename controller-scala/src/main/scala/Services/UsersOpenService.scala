import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait UsersOpenService extends ServiceTrait {
  final def open(username: String): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- UsersDAO.incrementOpens(username)

          data <- UsersDAO.open(username)

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
