import bundles.doobie.database.dc._

object UsersDAO {
  def count() = {
    run(
        quote(
            query[UsersList]
              .filter(!_.is_deleted.exists(_ == true))
              .size
        )
    )
  }

  def list(offset: Int, pageLength: Int) = {
    run(
        quote(
            query[UsersList]
              .filter(!_.is_deleted.exists(_ == true))
              .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
              .drop(lift(offset))
              .take(lift(pageLength))
        )
    )
  }
}
