import bundles.doobie.database.dc._

object UsersDAO {
  def count() = {
    run(
        quote(
            query[UsersWithMeta]
              .filter(_.deleted_at.isEmpty)
              .size
        )
    )
  }

  def list(offset: Int, pageLength: Int) = {
    run(
        quote(
            query[UsersWithMeta]
              .filter(_.deleted_at.isEmpty)
              .map(x =>
                (x.username, x.email_address, x.created_at, x.edited_at)
              )
              // .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
              .drop(lift(offset))
              .take(lift(pageLength))
        )
    )
  }

  def open(username: String) = {
    run(
        quote(
            query[UsersWithMeta].filter(_.username == lift(username))
        )
    )
      .map(_.head)
  }

  def softDelete(usernames: List[String]) = {
    run(
        quote(
            query[UsersWithMeta]
              .filter(x => liftQuery(usernames.toSet).contains(x.username))
              .delete
        )
    )
  }
}
