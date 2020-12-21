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

  def view(username: String) = {
    run(
        quote(
            query[UserView].filter(_.username == lift(username))
        )
    ).map(_.head)
  }
}
