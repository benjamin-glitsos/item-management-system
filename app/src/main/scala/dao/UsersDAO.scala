import java.sql.Timestamp
import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {

    implicit val ec: ExecutionContext = ExecutionContext.global

    private val full = for {
        u <- this
        p <- PeopleDAO if u.person_id === p.id
        r <- RecordsDAO if p.record_id === r.id
    } yield (
        u.id,
        u.username,
        u.password,
        p.first_name,
        p.last_name,
        r.created_at,
        r.updated_at,
        r.deleted_at
    )

    def list(rows: Int, page: Int): Future[Seq[(Int, String, String, String, String, Option[Timestamp], Option[Timestamp], Option[Timestamp])]] = {
        db.run(full.drop((page - 1) * rows).take(rows).result) // TODO: make the last page return a full list of the last items rather than nothing. requires maths.
    }

    def show(id: Int): Future[Option[User]] = {
        db.run(this.filter(_.id === id).result).map(_.headOption)
    }

    def delete(id: Int): Future[Int] = {
        db.run(this.filter(_.id === id).delete)
    }

    // def delete(id: Int): Future[Int] = {
    //     users.filter(_.id === id).deleted_at.update(Seeder.currentTimestamp()).run
    // }
}
