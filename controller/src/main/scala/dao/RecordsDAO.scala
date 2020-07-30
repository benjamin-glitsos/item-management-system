import java.util.UUID
import java.time.LocalDateTime;
import io.getquill._

object RecordsDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    implicit val encodeUUID = MappedEncoding[UUID, String](_.toString)
    implicit val timestampEncoder = MappedEncoding[LocalDateTime, String](_.format("dd-MM-yyyy HH:mm:ss"))

    def now(): Timestamp = {
        LocalDateTime.now()
    }

    implicit val recordsInsertMeta = insertMeta[Records](_.id)

    def upsert(uuid: UUID, user_id: Int): Unit = {
        ctx.translate(
            quote {
                query[Records]
                    .insert(
                        _.uuid -> lift(uuid),
                        _.created_at -> lift(now()),
                        _.created_by -> lift(user_id)
                    )
                    // .onConflictUpdate(_.uuid)(
                    //     (t, e) => t.updated_at -> now(),
                    //     (t, e) => t.updated_by -> user_id
                    // )
            }
        )
    }
}
