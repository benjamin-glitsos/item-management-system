// import bundles.doobie.database._
import bundles.doobie.database.dc._
// import doobie._

object UsersDAO extends LogicUtilities {
  def count() = {
    run(
        quote(
            query[UsersList]
              .filter(!_.is_deleted.exists(_ == true))
              .size
        )
    )
  }

  def list(offset: Int, length: Int) = {
    run(
        quote(
            query[UsersList]
              .filter(!_.is_deleted.exists(_ == true))
              .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
              .drop(lift(offset))
              .take(lift(length))
        )
    )
  }
}
