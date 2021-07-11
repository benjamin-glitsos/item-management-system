trait UsersOpenDAO extends DoobieDatabaseMixin {
  import dc._

  final def open(username: String) = {
    run(
      quote(
        query[UsersOpen].filter(_.username == lift(username))
      )
    ).map(_.head)
  }

  final def incrementOpens(username: String) = {
    run(
      quote(
        query[UsersOpen]
          .filter(_.username == lift(username))
          .update(x => x.opens -> (x.opens + 1))
      )
    )
  }
}
