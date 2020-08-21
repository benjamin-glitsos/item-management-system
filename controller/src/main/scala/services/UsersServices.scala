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

object UsersServices {
    def insert(u: User, user_id: Int, notes: Option[String]) = {
        for {
          r_id <- RecordsDAO.insert(user_id, notes)
          _ <- UsersDAO.insert(u.copy(record_id = r_id))
        } yield ()
    }

    def update(u: User, user_id: Int, notes: Option[String]) = {
        for {
          _ <- RecordsDAO.update(
              id = u.record_id,
              user_id,
              notes
          )
          _ <- UsersDAO.update(u)
        } yield ()
    }

    def delete(record_id: Int, user_id: Int) = {
        RecordsDAO.delete(record_id, user_id)
    }

    def restore(record_id: Int, user_id: Int) = {
        RecordsDAO.restore(record_id, user_id)
    }

    def list() = {
        UsersDAO.list()
    }

    // def view(u_id: Int) = {}
}
