import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {

    implicit val ec: ExecutionContext = ExecutionContext.global

    // TODO: cache in Redis
    // TODO: add lifted UserFull type annotation. Rep[UserFull] ?
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
        user = User(
            id = u.id,
            person_id = u.person_id,
            username = u.username,
            password = u.password
        )
        person = Person(
            id = p.id,
            record_id = p.record_id,
            first_name = p.first_name,
            last_name = p.last_name,
            other_names = p.other_names,
            sex_id = p.sex_id,
            email_address = p.email_address,
            phone_number = p.phone_number,
            address_line_one = p.address_line_one,
            address_line_two = p.address_line_two,
            zip = p.zip
        )
        sex = Sex(
            id = s.id,
            name = s.name
        )
        record_metadata = RecordMetadata(
            created_at = r.created_at,
            updated_at = r.updated_at,
            deleted_at = r.deleted_at,
            id = cu.id,
            first_name = cp.first_name,
            last_name = cp.last_name,
            id = uu.id,
            first_name = up.first_name,
            last_name = up.last_name,
            id = du.id,
            first_name = dp.first_name,
            last_name = dp.last_name
        )
    } yield (
        user,
        person,
        sex,
        record_metadata
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
