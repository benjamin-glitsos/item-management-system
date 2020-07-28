import io.getquill._

object UsersDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def list() = {
        ctx.run(
            quote {
                query[Users]
            }
        )
    }
}
