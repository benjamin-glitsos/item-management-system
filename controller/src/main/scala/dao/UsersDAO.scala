import io.getquill._

object UsersDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def create(item: Users) = {
        ctx.run(
            quote {
                query[Users].insert(lift(item))
            }
        )
    }
}
