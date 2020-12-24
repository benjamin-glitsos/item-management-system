import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import upickle_bundle.implicits._

trait UsersServicesOpen {
  def open(username: String) = {
    (for {
      _ <- UsersDAO.incrementOpens(username)

      data <- UsersDAO.open(username)

      val output = write(
        ujson.Obj(
          "data" -> writeJs(data)
        )
      )
    } yield (output))
      .transact(xa)
      .unsafeRunSync
  }
}
