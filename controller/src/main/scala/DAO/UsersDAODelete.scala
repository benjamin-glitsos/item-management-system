import doobie_bundle.database.dc._
import java.time.LocalDateTime

trait UsersDAODelete {
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
