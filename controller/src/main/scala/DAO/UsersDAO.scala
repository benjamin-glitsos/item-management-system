import io.getquill._

object UsersDAO {
  val ctx = new SqlMirrorContext(MirrorSqlDialect, Literal)
  import ctx._

  def list(offset: Int, length: Int) = {
    run(
        quote(
            query[UsersList]
              .filter(!_.is_deleted.exists(_ == true))
              .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
              .drop(lift(offset))
              .take(lift(length))
        )
    )
  }
}
