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

    def insert(u: User) = {
        val q = quote {
            query[User].insert(
                _.record_id -> lift(u.record_id),
                _.staff_id -> lift(u.staff_id),
                _.username -> lift(u.username),
                _.password -> lift(u.password)
            )
        }
        run(q)
    }

    def update(u: User) = {
        val q = quote {
            query[User]
                .filter(x => x.record_id == lift(u.record_id))
                .update(
                    _.username -> lift(u.username),
                    _.password -> lift(u.password)
                )
        }
        run(q)
    }

    def list(p: Page) = {
        val q = quote {
            (for {
                u <- query[User]
                r <- query[Record]
                    .join(_.id == u.record_id)
                    .filter(_.deleted_at.isEmpty)
                creator <- query[User].join(_.id == r.created_by)
            } yield (UserList(
                    username = lift(u.username),
                    created_at = lift(r.created_at),
                    created_by = lift(creator.username),
                    edited_at = lift(None),
                    edited_by = lift(None)
                )))
                .drop((lift(p.number) - 1) * lift(p.length))
                .take(lift(p.length))
        }
        run(q)
    }

    // TODO: for view() return (u, r). These will contain User() and Record() which will become the json
}
// .sortBy(x => (x.edited_at, x.created_at)) // (Ord(Ord.descNullsLast, Ord.descNullsLast))
// editor <- query[User].join(_.id == r.edited_by)
// edited_at = r.edited_at,
// edited_by = Some(editor.username),
