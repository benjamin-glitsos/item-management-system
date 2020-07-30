import java.util.UUID
import java.time.LocalDateTime;
import io.getquill._

object RecordsDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    implicit val encodeUUID = MappedEncoding[UUID, String](_.toString)
    // implicit val encodeLocalDateTime = MappedEncoding[LocalDateTime, String](_.format("dd-MM-yyyy HH:mm:ss"))

    implicit val recordsInsertMeta = insertMeta[Records](_.id)

    def upsert(uuid: UUID, user_id: Int): Unit = {
        ctx.translate(
            quote {
                query[Records]
                    .insert(
                        _.uuid -> lift(uuid),
                        _.created_at -> lift(LocalDateTime.now()),
                        _.created_by -> lift(user_id)
                    )
                    .onConflictUpdate(_.uuid)(
                        (t, e) => t.updated_at -> lift(Some(LocalDateTime.now()): Option[LocalDateTime]),
                        (t, e) => t.updated_by -> lift(Some(user_id): Option[Int])
                    )
            }
        )
    }
}
