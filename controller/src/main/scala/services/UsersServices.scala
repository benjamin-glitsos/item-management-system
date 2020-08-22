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

    def delete(record_id: Int, user_id: Int) = {
        RecordsDAO.delete(record_id, user_id)
    }

    def restore(record_id: Int, user_id: Int) = {
        RecordsDAO.restore(record_id, user_id)
    }

    def list(p: Page) = {
        UsersDAO.list(p: Page)
    }

    def open(username: String, user_id: Int) = {
        for {
          u <- UsersDAO.open(username)
          val record_id = u.head.record_id
          r <- RecordsDAO.open(
              id = record_id,
              user_id
          )
          _ <- RecordsDAO.opened(
              id = record_id,
              user_id
          )
        } yield (u.head, r.head)
        // TODO: make a custom class mapping for Doobie to allow your nested case classes (UserOpen) to convert into Json
    }

    // TODO: next and prev services will get the username of the next user and then run the 'open' service using that
}
