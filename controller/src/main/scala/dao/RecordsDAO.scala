import java.util.UUID
import java.time.Instant
import java.sql.{Timestamp, Types}
import io.getquill._

object RecordsDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    implicit val encodeUUID = MappedEncoding[UUID, String](_.toString)
    implicit val instantEncoder = MappedEncoding[Instant, Timestamp](i => Timestamp.from(i))

    // implicit def encodeInstant(implicit ctx: PostgresContext): ctx.Encoder[Instant] =
    //     ctx.encoder(
    //         Types.TIMESTAMP,
    //         (i, ts, r) => r.setTimestamp(i, Timestamp.from(ts))
    //     )
    // implicit val encodeInstant: Encoder[Instant] =
    //     encoder(
    //         Types.TIMESTAMP,
    //         (index, value, row) => r.setTimestamp(index, Timestamp.from(value), Types.TIMESTAMP)
    //     )

    implicit val recordsInsertMeta = insertMeta[Records](_.id)

    def upsert(uuid: UUID, user_id: Int): Unit = {
        ctx.run(
            quote {
                query[Records]
                    .insert(
                        _.uuid -> lift(uuid),
                        _.created_at -> lift(Instant.now()),
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
