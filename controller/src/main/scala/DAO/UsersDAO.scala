import bundles.doobie.database.dc._
import java.time.LocalDateTime

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
              .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
              .map(x =>
                (x.username, x.email_address, x.created_at, x.edited_at)
              )
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
              .update(_.deleted_at -> lift(Option(LocalDateTime.now())))
        )
    )
  }

  def restoreDelete(usernames: List[String]) = {
    run(
        quote(
            query[UsersWithMeta]
              .filter(x => liftQuery(usernames.toSet).contains(x.username))
              .update(_.restored_at -> lift(Option(LocalDateTime.now())))
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
}
