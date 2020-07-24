import scala.concurrent._
import slick.jdbc.PostgresProfile.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) {

    implicit val ec: ExecutionContext = ExecutionContext.global

    // private val list = for {
    //     u <- this
    //     p <- PeopleDAO if u.person_id === p.id
    //     s <- SexDAO if p.sex_id === s.id
    //     r <- RecordsDAO if p.record_id === r.id
    //     cu <- this if r.created_by === cu.id
    //     cp <- PeopleDAO if cu.person_id === cp.id
    //     uu <- this if r.updated_by === uu.id
    //     up <- PeopleDAO if uu.person_id === up.id
    //     du <- this if r.deleted_by === du.id
    //     dp <- PeopleDAO if du.person_id === dp.id
    // } yield (

    // TODO: add lifted UsersList type annotation. Rep[UsersList] ?

    private def listData = for {
        u <- this
        p <- PeopleDAO if u.person_id === p.id
        s <- SexDAO if p.sex_id === s.id
        r <- RecordsDAO if p.record_id === r.id && r.deleted_at !== None
    } yield (
        u.username,
        p.first_name,
        p.last_name,
        r.created_at,
        r.deleted_at
    )

    private def item(id: Int) = this.filter(_.id === id)
    // Future[Option[User]]

    def list(rows: Int, page: Int) = {
        // Future[Seq[UsersList]]
        listData.drop((page - 1) * rows).take(rows).result
        // TODO: make the last page return a full list of the last items rather than nothing. requires maths.
    }

    def show(id: Int) = {
        // Future[Option[User]]
        item(id).result.flatMap(_.headOption)
    }

    def delete(id: Int) = {
        // Future[Int]
        item(id).delete
    }

    // def delete(id: Int): Future[Int] = {
    //     users.filter(_.id === id).deleted_at.update(Seeder.currentTimestamp()).run
    // }



}
