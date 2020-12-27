import doobie_bundle.database.dc._

trait UsersDAODelete {
  def softDelete(usernames: List[String]) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(x => liftQuery(usernames.toSet).contains(x.username))
          .update(_.is_deleted -> lift(true))
      )
    )
  }

  def restoreDelete(usernames: List[String]) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(x => liftQuery(usernames.toSet).contains(x.username))
          .update(_.is_deleted -> lift(false))
      )
    )
  }

  def hardDelete(usernames: List[String]) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(x => liftQuery(usernames.toSet).contains(x.username))
          .delete
      )
    )
  }

  def hardDeleteAllRows() = {
    run(
      quote(
        query[UsersWithMeta].delete
      )
    )
  }
}
