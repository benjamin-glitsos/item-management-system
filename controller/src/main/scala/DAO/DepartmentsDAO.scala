import bundles.doobie.database._
import bundles.doobie.database.dc._
import doobie._

object DepartmentsDAO {
    val name = sys.env.getOrElse("DEPARTMENTS_TABLE", "departments")

    def count(): ConnectionIO[Long] = {
        run(
            quote(
                query[Department]
            ).size
        )
    }
}
