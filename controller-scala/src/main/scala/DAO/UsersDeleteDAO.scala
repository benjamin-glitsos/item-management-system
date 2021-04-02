import doobie_bundle.database.dc._

trait UsersDeleteDAO {
  final def softDelete(usernames: List[String]) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(x => liftQuery(usernames.toSet).contains(x.username))
          .update(_.is_deleted -> lift(true))
      )
    )
  }

  final def restoreDelete(usernames: List[String]) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(x => liftQuery(usernames.toSet).contains(x.username))
          .update(_.is_deleted -> lift(false))
      )
    )
  }

  final def hardDelete(usernames: List[String]) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(x => liftQuery(usernames.toSet).contains(x.username))
          .delete
      )
    )
  }

  final def hardDeleteAllRows() = {
    run(
      quote(
        query[UsersWithMeta].delete
      )
    )
  }
}
