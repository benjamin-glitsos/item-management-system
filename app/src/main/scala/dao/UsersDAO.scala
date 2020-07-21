import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO
    extends TableQuery(new UsersSchema(_))
    with Connection
    with Queries {

    implicit val ec: ExecutionContext = ExecutionContext.global

    def list(rows: Int, page: Int): Future[Seq[User]] = {
        db.run(this.drop((page - 1) * rows).take(rows).result) // TODO: make the last page return a full list of the last items rather than nothing. requires maths.
    }

    def show(id: Int): Future[Option[User]] = {
        db.run(this.filter(_.id === id).result).map(_.headOption)
    }

    def delete(id: Int): Future[Int] = {
        db.run(this.filter(_.id === id).delete) // TODO: change to soft delete
    }
}
