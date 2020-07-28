import io.getquill._

object UsersServices {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def create(item: Users) = {
        UsersDAO.create(item)
    }
}
