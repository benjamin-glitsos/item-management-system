import io.getquill._

object UsersDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def upsert(item: Users): Unit = {
        ctx.run(
            quote {
                query[Users]
                    .insert(lift(item))
                    .onConflictUpdate(_.id)(
                        (t, e) => t.username -> e.username,
                        (t, e) => t.password -> e.password
                    )
            }
        )
    }
}
