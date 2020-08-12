import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import java.util.UUID
import java.time.LocalDateTime

object Main {
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    case class Record2(
        id: Int,
        updated_by: Option[Int],
    )

    val program3: ConnectionIO[(Int, Int)] =
      for {
        r <- sql"""
            INSERT INTO records (uuid, created_at, created_by)
            VALUES ('846bc87f-4efe-43cb-9a57-75e20f96db6f', '2020-08-11 08:28:09.517903', 1)
            ON CONFLICT (uuid)
            DO UPDATE SET
                  updated_at = EXCLUDED.created_at
                , updated_by = EXCLUDED.created_by
            RETURNING id, updated_by
            """.query[Record2].unique
        val isNew = r.updated_by.isDefined
        b <- if (isNew) {
                sql"select 1".query[Int].unique
            } else {
                sql"select 0".query[Int].unique
            }
      } yield (r.id, b)

    def main(args: Array[String]) {
        println(program3.transact(xa).unsafeRunSync)
    }
}

// upsertAll(
//     record = RecordEdit(
//         uuid = UUID.randomUUID,
//         time = LocalDateTime.now(),
//         user_id = 1
//     ),
//     user = User(
//         id = 0,
//         person_id = 0,
//         username = "un4",
//         password = "pw6"
//     )
// ).run.transact(xa).unsafeRunSync

// | WITH up_record AS (
// |     INSERT INTO records (uuid, created_at, created_by)
// |     VALUES (${record.uuid}, ${record.time}, ${record.user_id})
// |     ON CONFLICT (uuid)
// |     DO UPDATE SET updated_at = EXCLUDED.created_at, updated_by = EXCLUDED.created_by
// |     RETURNING id
// | )
// | INSERT INTO users (person_id, username, password)
// | VALUES (${user.person_id}, ${user.username}, ${user.password})
// | ON CONFLICT (username)
// | DO UPDATE SET person_id = EXCLUDED.person_id, password = EXCLUDED.password

// DO $$
// BEGIN
//     WITH up_record AS (
//         INSERT INTO records (uuid, created_at, created_by)
//         VALUES ('746bc87f-4efe-43cb-8a57-75e20f96db6f', '2020-08-11 08:28:09.517903', 1)
//         ON CONFLICT (uuid)
//         DO UPDATE SET
//               updated_at = EXCLUDED.created_at
//             , updated_by = EXCLUDED.created_by
//         RETURNING id AS up_record_id
//                 , CAST(updated_by AS BOOLEAN) AS is_new;
//     )
//     IF is_new THEN
//         WITH up_person AS (
//             INSERT INTO people (
//                 record_id
//               , first_name
//               , last_name
//               , other_names
//               , sex_id
//               , email_address
//               , phone_number
//               , address_line_one
//               , address_line_two
//               , zip
//             ) VALUES (
//                 up_record_id
//               , 'fn'
//               , 'ln'
//               , 'on'
//               , 1
//               , 'test@example.com'
//               , '0444444444'
//               , '1 Test St'
//               , 'Sydney NSW'
//               , '2000'
//             ) RETURNING id AS up_person_id;
//         )
//         INSERT INTO users (person_id, username, password)
//         VALUES (up_person_id, 'un', 'pw');
//     ELSE
//         WITH up_person AS (
//             UPDATE people SET
//                 first_name = 'fn'
//               , last_name = 'ln'
//               , other_names = 'on'
//               , sex_id = 1
//               , email_address = 'test@example.com'
//               , phone_number = '0444444444'
//               , address_line_one = '1 Test St'
//               , address_line_two = 'Sydney NSW'
//               , zip = '2000'
//             WHERE record_id = up_record_id
//             RETURNING id AS up_person_id;
//         )
//         UPDATE users SET
//             username = 'un'
//           , password = 'pw';
//     END IF;
// END
// $$;
