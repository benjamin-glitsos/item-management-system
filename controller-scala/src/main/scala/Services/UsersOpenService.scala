import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait UsersOpenService
    extends ServiceMixin
    with DoobieConnectionMixin
    with UpickleMixin {
  final def open(username: String): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- UsersDAO.incrementOpens(username)

          data <- UsersDAO.open(username)

          val output: String = createDataOutput(writeJs(data))

        } yield (output)).transact(transactor).unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
