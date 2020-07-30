import java.sql.Timestamp
import io.getquill._

object RecordsDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    // implicit val recordsInsertMeta = insertMeta[Records](_.id)

    // val now = quote {
    //   infix"NOW()".as[Timestamp]
    // }
    //
    // def upsert(id: Int, user_id: Int): Unit = {
    //     ctx.run(
    //         quote {
    //             query[Records]
    //                 .insert(id -> id, created_at -> now, created_by -> user_id)
    //                 .onConflictUpdate(_.id)(
    //                     (t, e) => t.updated_at -> now,
    //                     (t, e) => t.created_by -> user_id
    //                 )
    //         }
    //     )
    // }
}
