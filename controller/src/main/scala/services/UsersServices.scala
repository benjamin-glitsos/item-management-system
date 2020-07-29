import io.getquill._

object UsersServices {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def createOrUpdate(
        id: Int,
        user_id: Int,
        user: Users,
        person: People
    ): Unit = {
        RecordsDAO.upsert(id, user_id).returningGenerated(r => {
            PeopleDAO.upsert(
                person.copy(records_id = r.id)
            ).returningGenerated(p => {
                UsersDAO.upsert(
                    user.copy(person_id = p.id)
                )
            })
        })
    }
}
