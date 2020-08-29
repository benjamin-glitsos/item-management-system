import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import java.util.UUID
import java.time.LocalDateTime
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._
import java.util.UUID

object StaffServices {
    def create(
        s: Staff,
        p: Person,
        d_ids: List[Int],
        user_id: Int,
        notes: Option[String]
    ) = {
        for {
          r_id <- RecordsDAO.create(user_id, notes)
          p_id <- PeopleDAO.create(p)
          s_id <- StaffDAO.create(s.copy(
              record_id = r_id,
              person_id = p_id
          ))
          _ <- StaffDAO.assignDepartments(
              staff_id = s_id,
              department_ids = d
          )
        } yield ()
    }
}
