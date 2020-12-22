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
              .map(
                  _.username,
                  _.email_address,
                  _.created_at,
                  _.edited_at
              )
              .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
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
            // TODO: doesnt work because postgres view isn't updatable since it references more than one table. Solution is:
            // https://vibhorkumar.wordpress.com/2011/10/28/instead-of-trigger/
            // TODO: move the properties from delete-users.json to definitions/deletion.json. Name them 'method' and 'keysOfItems'
        )
    )
  }
}
