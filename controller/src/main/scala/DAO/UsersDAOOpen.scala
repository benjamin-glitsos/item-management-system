import bundles.doobie.database.dc._

trait UsersDAOOpen {
  def open(username: String) = {
    run(
        quote(
            query[UsersWithMeta].filter(_.username == lift(username))
        )
    ).map(_.head)
  }
}
