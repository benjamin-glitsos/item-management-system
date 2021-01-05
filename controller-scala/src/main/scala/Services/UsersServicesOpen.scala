import doobie.implicits._
import doobie_bundle.connection._
import upickle.default._
import upickle_bundle.general._

trait UsersServicesOpen {
  final def open(username: String): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- UsersDAO.incrementOpens(username)

          data <- UsersDAO.open(username)

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
