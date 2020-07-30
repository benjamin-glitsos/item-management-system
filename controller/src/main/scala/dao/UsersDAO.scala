import io.getquill._

object UsersDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    implicit val usersInsertMeta = insertMeta[Users](_.id)

    def upsert(item: Users): Unit = {
        ctx.run(
            quote {
                query[Users]
                    .insert(lift(item))
                    .onConflictUpdate(_.uuid)(
                        (t, e) => t.person_id -> e.person_id,
                        (t, e) => t.username -> e.username,
                        (t, e) => t.password -> e.password
                    )
            }
        )
    }
}
