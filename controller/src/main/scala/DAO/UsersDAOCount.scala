import bundles.doobie.database.dc._

trait UsersDAOCount {
  def count() = {
    run(
        quote(
            query[UsersWithMeta]
              .filter(_.deleted_at.isEmpty)
              .size
        )
    )
  }
}
