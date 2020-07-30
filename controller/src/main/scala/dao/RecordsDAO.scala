import java.util.UUID
import java.sql.Timestamp
import io.getquill._

object RecordsDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    implicit val recordsInsertMeta = insertMeta[Records](_.id)

    def now(): Timestamp = {
        new Timestamp(System.currentTimeMillis())
    }

    def upsert(uuid: UUID, user_id: Int): Unit = {
        ctx.run(
            quote {
                query[Records]
                    .insert(uuid -> uuid, created_at -> now().toString, created_by -> user_id)
                    .onConflictUpdate(_.uuid)(
                        (t, e) => t.updated_at -> now(),
                        (t, e) => t.updated_by -> user_id
                    )
            }
        )
    }
}
