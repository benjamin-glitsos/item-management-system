import io.getquill._

object PeopleDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def upsert(item: People): Unit = {
        ctx.run(
            quote {
                query[People]
                    .insert(lift(item))
                    .onConflictUpdate(_.id)(
                        (t, e) => t.first_name -> e.first_name
                        // (t, e) => t.last_name -> e.last_name
                        // (t, e) => t.other_names -> e.other_names,
                        // (t, e) => t.sex_id -> e.sex_id,
                        // (t, e) => t.email_address -> e.email_address,
                        // (t, e) => t.phone_number -> e.phone_number,
                        // (t, e) => t.address_line_one -> e.address_line_one,
                        // (t, e) => t.address_line_two -> e.address_line_two,
                        // (t, e) => t.zip -> e.zip
                    )
            }
        )
    }
}
