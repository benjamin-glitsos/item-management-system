import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {
    def list(): Future[Seq[User]] = {
        db.run(this.result)
        // request
    }
}
