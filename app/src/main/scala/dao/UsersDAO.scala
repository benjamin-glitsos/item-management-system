import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {
    def list(): Future[Seq[User]] = {
        db.run(this.result)
    }

    def show(id: Int): Future[Seq[User]] = {
        db.run(this.result.filter(_.id === id))
    }
}
