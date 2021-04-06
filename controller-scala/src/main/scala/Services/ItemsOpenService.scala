import doobie.implicits._
import doobie_bundle.connection._
import upickle.default._
import upickle_bundle.general._

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
