import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import io.circe.syntax._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {
    def all(): Future[Seq[User]] = {
        db.run(this.result)
    }
}
