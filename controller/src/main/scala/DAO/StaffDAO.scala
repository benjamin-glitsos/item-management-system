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

object StaffDAO {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val usersSchemaMeta = schemaMeta[User]("staff")
    implicit val usersSchemaMeta = schemaMeta[User]("users")
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    def summary(id: Int) = {
        run(quote(
            // (for {
            //     u <- query[User].filter(_.username == lift(username))
            //     r <- query[Record].join(_.id == u.record_id)
            // } yield (u))
        ))
    }
}
