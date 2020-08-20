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

object UsersServices {
    def insert(r: RecordEdit, u: User): ConnectionIO[Unit] = {
        for {
          r_id <- RecordsDAO.insert(r)
          _ <- UsersDAO.insert(u.copy(record_id = r_id))
        } yield ()
    }

    def update(r: RecordEdit, u: User): ConnectionIO[Unit] = {
        for {
          _ <- RecordsDAO.update(r)
          _ <- UsersDAO.update(u)
        } yield ()
    }
}
