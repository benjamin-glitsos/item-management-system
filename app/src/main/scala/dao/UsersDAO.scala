import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) {

    implicit val ec: ExecutionContext = ExecutionContext.global

    // TODO: add lifted UserSummary type annotation. Rep[UserSummary] ?
    private val summary = for {
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
        User(
            u.id,
            u.person_id,
            u.username,
            u.password
        ),
        Person(
            p.id,
            p.record_id,
            p.first_name,
            p.last_name,
            p.other_names,
            p.sex_id,
            p.email_address,
            p.phone_number,
            p.address_line_one,
            p.address_line_two,
            p.zip
        ),
        Sex(
            s.id,
            s.name
        ),
        RecordMetadata(
            created_at = r.created_at,
            created_by = PersonName(
                id,
                first_name,
                last_name,
                other_names
            ),
            updated_at = r.updated_at,
            updated_by = PersonName(
                id,
                first_name,
                last_name,
                other_names
            ),
            deleted_at = r.deleted_at,
            deleted_by = PersonName(
                id,
                first_name,
                last_name,
                other_names
            )
        )
    )

    private def item(id: Int): Future[Option[User]] = this.filter(_.id === id)

    def list(rows: Int, page: Int): Future[Seq[UserSummary]] = UsersService.list(rows, page)

    def show(id: Int): Future[Option[User]] = UsersService.show(id)

    def delete(id: Int): Future[Int] = UsersService.delete(id)
}
