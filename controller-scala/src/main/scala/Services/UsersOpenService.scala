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
          .transact(xa)
          .unsafeRunSync
      } catch {
        case e: java.sql.SQLException =>
          System.err.println(e.getMessage)
          System.err.println(e.getSQLState)
          new String
      }
    )
  }
}
