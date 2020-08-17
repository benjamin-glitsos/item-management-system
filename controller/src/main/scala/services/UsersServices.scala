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
    def upsert(
        record: RecordEdit,
        user: User
    ): ConnectionIO[Unit] = {
        for {
          r <- RecordsDAO.upsert(record)
          val u = user.copy(record_id = r.id)
          _ <- if (r.edited_by.isEmpty) {
                  UsersDAO.insert(u)
              } else {
                  UsersDAO.update(u)
              }
        } yield ()
    }
}
