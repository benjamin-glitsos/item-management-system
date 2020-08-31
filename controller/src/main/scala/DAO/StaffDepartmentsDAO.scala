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

object StaffDepartmentsDAO {
    val name = s"${StaffDAO.name}_${DepartmentsDAO.name}"

    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val staffSchemaMeta = schemaMeta[User]("staff")
    implicit val staffDepartmentsSchemaMeta = schemaMeta[StaffDepartment]("staff_departments")
    implicit val personSchemaMeta = schemaMeta[Person]("people")
    implicit val usersSchemaMeta = schemaMeta[User]("users")
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    def create(staff_id: Int, department_ids: List[Int]) = {
        val staffDepartments = department_ids.map(department_id => StaffDepartment(staff_id, department_id))
        run(quote(
            liftQuery(staffDepartments).foreach(x => query[StaffDepartment].insert(x))
        ))
    }
}
