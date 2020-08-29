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

object DepartmentsDAO {
    val name = sys.env.getOrElse("DEPARTMENTS_TABLE", "departments")

    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val staffSchemaMeta = schemaMeta[User]("staff")
    implicit val staffDepartmentsSchemaMeta = schemaMeta[StaffDepartment]("staff_departments")
    implicit val departmentsSchemaMeta = schemaMeta[Department]("departments")
    implicit val personSchemaMeta = schemaMeta[Person]("people")
    implicit val usersSchemaMeta = schemaMeta[User]("users")
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    def count() = {
        run(
            quote(
                query[Department]
            ).size
        )
    }
}
