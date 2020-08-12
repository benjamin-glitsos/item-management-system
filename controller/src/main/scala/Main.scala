import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import java.util.UUID
import java.time.LocalDateTime
import doobie.postgres._
import doobie.postgres.implicits._

object Main {
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    case class RecordEdit(
        uuid: UUID,
        user_id: Int
    )

    case class RecordReturn(
        id: Int,
        updated_by: Option[Int]
    )

    case class PersonEdit(
        first_name: String,
        last_name: String,
        other_names: Option[String],
        sex_id: Int,
        email_address: String,
        phone_number: String,
        address_line_one: String,
        address_line_two: String,
        zip: String
    )

    case class UserEdit(
        username: String,
        password: String
    )

    def upsert(record: RecordEdit, person: PersonEdit, user: UserEdit): ConnectionIO[Unit] = {
        for {
          r <- sql"""
              INSERT INTO records (uuid, created_at, created_by)
              VALUES (${record.uuid}, NOW(), ${record.user_id})
              ON CONFLICT (uuid)
              DO UPDATE SET
                    updated_at = EXCLUDED.created_at
                  , updated_by = EXCLUDED.created_by
              RETURNING id, updated_by
              """.query[RecordReturn].unique
          _ <- if (r.updated_by.isEmpty) {
                  sql"""
                  WITH new_person AS (
                      INSERT INTO people (
                        record_id
                      , first_name
                      , last_name
                      , other_names
                      , sex_id
                      , email_address
                      , phone_number
                      , address_line_one
                      , address_line_two
                      , zip
                      ) VALUES (
                            ${r.id}
                          , ${person.first_name}
                          , ${person.last_name}
                          , ${person.other_names}
                          , ${person.sex_id}
                          , ${person.email_address}
                          , ${person.phone_number}
                          , ${person.address_line_one}
                          , ${person.address_line_two}
                          , ${person.zip}
                      ) RETURNING id
                  )
                  INSERT INTO users (person_id, username, password)
                  VALUES (
                      (SELECT id FROM new_person)
                    , ${user.username}
                    , ${user.password}
                  )
                  """.update.run
              } else {
                  sql"""
                  WITH existing_person AS (
                      UPDATE people SET
                          first_name       = ${person.first_name}
                        , last_name        = ${person.last_name}
                        , other_names      = ${person.other_names}
                        , sex_id           = ${person.sex_id}
                        , email_address    = ${person.email_address}
                        , phone_number     = ${person.phone_number}
                        , address_line_one = ${person.address_line_one}
                        , address_line_two = ${person.address_line_two}
                        , zip              = ${person.zip}
                      WHERE record_id      = ${r.id}
                      RETURNING id
                  )
                  UPDATE users SET
                      username = ${user.username}
                    , password = ${user.password}
                  WHERE person_id = (SELECT id FROM existing_person)
                  """.update.run
              }
        } yield ()
    }

    def main(args: Array[String]) {
        upsert(
            RecordEdit(
                uuid = java.util.UUID.randomUUID,
                user_id = 1
            ),
            PersonEdit(
                first_name = "fn2",
                last_name = "ln2",
                other_names = Some("on2"),
                sex_id = 1,
                email_address = "test@example.com",
                phone_number = "0444444444",
                address_line_one = "1 Test St",
                address_line_two = "Sydney",
                zip = "2000"
            ),
            UserEdit(
                username = "un3",
                password = "pw3"
            )
        ).transact(xa).unsafeRunSync
    }
}
