import bundles.doobie.database._
import bundles.doobie.database.dc._
import doobie._

object DepartmentsDAO {
    def count() = {
        run(
            quote(
                query[Department]
            ).size
        )
    }
}
