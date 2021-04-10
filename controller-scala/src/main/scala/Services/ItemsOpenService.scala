import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ItemsOpenService {
  final def open(key: String): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- ItemsDAO.incrementOpens(key)

          data <- ItemsDAO.open(key)

          val output: String = write(
            ujson.Obj(
              "data" -> writeJs(data)
            )
          )
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
