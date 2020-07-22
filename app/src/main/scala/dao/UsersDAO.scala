import java.sql.Timestamp
import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {

    implicit val ec: ExecutionContext = ExecutionContext.global

    // TODO: cache in Redis
    // TODO: add listed UserFull type annotation
    private val full = for {
        u <- this
        p <- PeopleDAO if u.person_id === p.id
        s <- SexDAO if p.sex_id === s.id
        r <- RecordsDAO if p.record_id === r.id
        cu <- this if r.created_by === cu.id
        cp <- PeopleDAO if cu.person_id === cp.id
        uu <- this if r.updated_by === uu.id
        up <- PeopleDAO if uu.person_id === up.id
        du <- this if r.deleted_by === du.id
        dp <- PeopleDAO if du.person_id === dp.id
    } yield (
        u.id,
        u.username,
        u.password,
        p.first_name,
        p.last_name,
        p.other_names,
        p.email_address,
        p.phone_number,
        p.address_line_one,
        p.address_line_two,
        p.zip,
        s.id,
        s.sex_name,
        r.created_at,
        r.updated_at,
        r.deleted_at,
        cu.id,
        cp.first_name,
        cp.last_name,
        uu.id,
        up.first_name,
        up.last_name,
        du.id,
        dp.first_name,
        dp.last_name
    )

    def list(rows: Int, page: Int): Future[Seq[UserFull]] = {
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
