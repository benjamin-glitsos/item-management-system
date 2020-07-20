import scala.concurrent.Future
import slick.driver.PostgresDriver.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {
    def list(): Future[Seq[User]] = {
        db.run(this.result)
        // request
    }
}
