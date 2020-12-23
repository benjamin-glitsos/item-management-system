import doobie_bundle.database.dc._

trait UsersDAOList {
  def list(offset: Int, pageLength: Int) = {
    run(
        quote(
            query[UsersWithMeta]
              .filter(_.deleted_at.isEmpty)
              .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
              .map(x =>
                (x.username, x.email_address, x.created_at, x.edited_at)
              )
              .drop(lift(offset))
              .take(lift(pageLength))
        )
    )
  }
}
