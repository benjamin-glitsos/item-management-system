import java.util.UUID
import java.sql.Timestamp
import io.getquill._

object RecordsDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    implicit val encodeUUID = MappedEncoding[UUID, String](_.toString)
    implicit val encodeTimestamp = MappedEncoding[Timestamp, String](_.toString)
    implicit val recordsInsertMeta = insertMeta[Records](_.id)

    def now(): Timestamp = {
        new Timestamp(System.currentTimeMillis())
    }

    def upsert(uuid: UUID, user_id: Int): Unit = {
        ctx.run(
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
