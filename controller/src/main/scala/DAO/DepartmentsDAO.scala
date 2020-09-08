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
import bundles.doobie.database._

object DepartmentsDAO {
    val name = sys.env.getOrElse("DEPARTMENTS_TABLE", "departments")

    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val departmentsSchemaMeta = schemaMeta[Department]("departments")

    def count() = {
        run(
            quote(
                query[Department]
            ).size
        )
    }
}
