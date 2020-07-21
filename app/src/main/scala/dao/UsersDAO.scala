import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {
    implicit val ec: ExecutionContext = ExecutionContext.global

    def list(): Future[Seq[User]] = {
        db.run(this.result)
    }

    def show(id: Int): Future[Option[User]] = {
        db.run(this.filter(_.id === id).result).map(_.headOption)
    }

    def delete(id: Int): Future[Int] = {
        db.run(this.filter(_.id === id).delete) // TODO: change to soft delete
    }
}
