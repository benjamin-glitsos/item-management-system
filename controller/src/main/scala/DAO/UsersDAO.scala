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
            query[UsersOpen].filter(_.username == lift(username))
        )
    )
      .map(_.head)
  }

  def softDelete(usernames: List[String]) = {
    run(
        quote(
            query[UsersOpen]
              .filter(x => liftQuery(usernames.toSet).contains(x.username))
              .update(_.is_deleted -> lift(true))
            // TODO: doesnt work because postgres view isn't updatable since it references more than one table. Solution is:
            // https://vibhorkumar.wordpress.com/2011/10/28/instead-of-trigger/
            // TODO: set deleted_by_id and deleted_at
        )
    )
  }
}
