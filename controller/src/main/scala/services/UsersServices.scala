import java.util.UUID
import io.getquill._

object UsersServices {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def createOrUpdate(
        uuid: UUID,
        user_id: Int,
        user: Users,
        person: People
    ): Unit = {
        RecordsDAO.upsert(uuid, user_id).returningGenerated(r => {
            PeopleDAO.upsert(
                person.copy(record_id = r.id)
            ).returningGenerated(p => {
                UsersDAO.upsert(
                    user.copy(
                        uuid = uuid,
                        person_id = p.id
                    )
                )
            })
        })
    }
}
