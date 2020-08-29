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

object DAO {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val staffSchemaMeta = schemaMeta[User]("staff")
    implicit val personSchemaMeta = schemaMeta[Person]("people")
    implicit val usersSchemaMeta = schemaMeta[User]("users")
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    // TODO: you need to move the super user inserting into this controller seeder in order for this restart process to work
    def truncateAll() = {
        sql"""
        TRUNCATE records, users, staff, people, patients, staff_departments, visits;
        """.update.run
    }
}
