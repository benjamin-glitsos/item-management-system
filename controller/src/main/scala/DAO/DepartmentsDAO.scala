import bundles.doobie.database._
import bundles.doobie.database.dc._

object DepartmentsDAO {
    val name = sys.env.getOrElse("DEPARTMENTS_TABLE", "departments")

    def count() = {
        run(
            quote(
                query[Department]
            ).size
        )
    }
}
