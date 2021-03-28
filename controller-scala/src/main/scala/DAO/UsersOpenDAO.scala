import doobie_bundle.database.dc._

trait UsersOpenDAO {
  final def open(username: String) = {
    run(
      quote(
        query[UsersWithMeta].filter(_.username == lift(username))
      )
    ).map(_.head)
  }

  final def incrementOpens(username: String) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(_.username == lift(username))
          .update(x => x.opens -> (x.opens + 1))
      )
    )
  }
}
