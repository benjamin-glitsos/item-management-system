import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._
import io.getquill.{ idiom => _, _ }
import doobie.quill.DoobieContext

object UsersDAO {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val usersSchemaMeta = schemaMeta[User]("users")
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    def create(u: User) = {
        run(quote(
            query[User].insert(
                _.record_id -> lift(u.record_id),
                _.staff_id -> lift(u.staff_id),
                _.username -> lift(u.username),
                _.password -> lift(u.password)
            )
        ))
    }

    def edit(u: User) = {
        run(quote(
            query[User]
                .filter(x => x.record_id == lift(u.record_id))
                .update(
                    _.username -> lift(u.username),
                    _.password -> lift(u.password)
                )
        ))
    }

    // TODO: don't decompose this into modular Records and Users lists because it needs to all be ordered together as one list
    def list(p: Page) = {
        run(quote(
            (for {
                u <- query[User]
                r <- query[Record]
                    .join(_.id == u.record_id)
                    .filter(_.deleted_at.isEmpty)
                creator <- query[User].join(_.id == r.created_by)
                editor <- query[User].leftJoin(x => r.edited_by.exists(_ == x.id))
            } yield (UserList(
                    username = u.username,
                    edited_at = r.edited_at,
                    edited_by = editor.map(_.username),
                    created_at = r.created_at,
                    created_by = creator.username
                )))
                    .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
                    .drop((lift(p.number) - 1) * lift(p.length))
                    .take(lift(p.length))
        ))
    }

    def open(username: String) = {
        run(quote(
            query[User].filter(_.username == lift(username))
        ))
    }
}

// StaffSummary(
//     first_name = lift("Peter"),
//     last_name = lift("Chen"),
//     staff_number = lift("1234567890")
// ),
