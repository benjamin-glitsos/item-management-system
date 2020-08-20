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
    def insert(u: User): ConnectionIO[Unit] = {
        for {
          r_id <- RecordsDAO.insert(
              id = u.record_id,
              user_id = u.id
          )
          _ <- UsersDAO.insert(u.copy(record_id = r_id))
        } yield ()
    }

    def update(u: User): ConnectionIO[Unit] = {
        for {
          _ <- RecordsDAO.update(
              id = u.record_id,
              user_id = u.id
          )
          _ <- UsersDAO.update(u)
        } yield ()
    }

    def delete(u: User): ConnectionIO[Unit] = {
        RecordsDAO.delete(
            id = u.record_id,
            user_id = u.id
        )
    }

    def restore(u: User): ConnectionIO[Unit] = {
        RecordsDAO.restore(id = u.record_id)
    }
}
