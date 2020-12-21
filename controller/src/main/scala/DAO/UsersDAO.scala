import bundles.doobie.database.dc._

object UsersDAO {
  def count() = {
    run(
        quote(
            query[UsersList]
              .filter(!_.is_deleted)
              .size
        )
    )
  }

  def list(offset: Int, pageLength: Int) = {
    run(
        quote(
            query[UsersList]
              .filter(!_.is_deleted)
              .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
              .drop(lift(offset))
              .take(lift(pageLength))
        )
    )
  }

  def open(username: String) = {
    run(
        quote(
            query[UserOpen].filter(_.username == lift(username))
        )
    ).map(_.head)
  }
}
