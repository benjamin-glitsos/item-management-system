import bundles.doobie.database._
import bundles.doobie.database.dc._

object StaffDepartmentsDAO {
    val name = s"${StaffDAO.name}_${DepartmentsDAO.name}"

    def create(staff_id: Int, department_ids: List[Int]) = {
        val staffDepartments = department_ids.map(department_id => StaffDepartment(staff_id, department_id))
        run(quote(
            liftQuery(staffDepartments).foreach(x => query[StaffDepartment].insert(x))
        ))
    }
}
