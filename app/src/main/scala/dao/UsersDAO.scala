import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {

    implicit val ec: ExecutionContext = ExecutionContext.global

    users = for {
        u <- this
        p <- PeopleDAO if u.id === p.id
    } yield (
        u._,
        p.record_id,
        p.first_name,
        p.last_name
    )

    def list(rows: Int, page: Int): Future[Seq[User]] = {
        db.run(users.drop((page - 1) * rows).take(rows).result) // TODO: make the last page return a full list of the last items rather than nothing. requires maths.
    }

    def show(id: Int): Future[Option[User]] = {
        db.run(users.filter(_.id === id).result).map(_.headOption)
    }

    // def delete(id: Int): Future[Int] = {
    //     users.filter(_.id === id).deleted_at.update(Seeder.currentTimestamp()).run
    // }
}
