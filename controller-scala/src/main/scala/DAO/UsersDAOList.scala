import doobie_bundle.database.dc._

trait UsersDAOList {
  final def list(offset: Int, pageLength: Int) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(!_.is_deleted)
          .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
          .map(x =>
            (
              x.username,
              x.email_address,
              x.first_name,
              x.last_name,
              x.other_names,
              x.created_at,
              x.edited_at
            )
          )
          .drop(lift(offset))
          .take(lift(pageLength))
      )
    )
  }
}
