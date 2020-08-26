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
    def create(u: User, user_id: Int, notes: Option[String]) = {
        for {
          r_id <- RecordsDAO.create(user_id, notes)
          _ <- UsersDAO.create(u.copy(record_id = r_id))
        } yield ()
    }

    def edit(u: User, user_id: Int, notes: Option[String]) = {
        for {
          _ <- RecordsDAO.edit(
              id = u.record_id,
              user_id,
              notes
          )
          _ <- UsersDAO.edit(u)
        } yield ()
    }

    def list(maybeNumber: Option[Int], maybeLength: Option[Int]) = {
        val number = maybeNumber.getOrElse(1)
        val length = maybeLength.getOrElse(25)

        UsersDAO.list(Page(number, length))
    }

    def open(username: String, user_id: Int) = {
        for {
          u <- UsersDAO.open(username)

          val record_id = u.head.record_id
          val staff_id = u.head.staff_id

          s <- StaffDAO.summary(staff_id)

          r <- RecordsDAO.open(
              id = record_id,
              user_id
          )

          _ <- RecordsDAO.opened(
              id = record_id,
              user_id
          )
        } yield (UserOpen(
            user = u.head,
            relations = List(s.head),
            record = r.head
        ))
    }

    def delete(username: String, user_id: Int) = {
        UsersDAO.delete(username, user_id)
    }

    def restore(username: String, user_id: Int) = {
        UsersDAO.restore(username, user_id)
    }

    def hardDelete(username: String, user_id: Int) = {
        UsersDAO.hardDelete(username, user_id)
    }

    // TODO: next and prev services will get the record_id of the next user and then run the 'open' service using that. if its the last record, then it will loop to the first again
}
