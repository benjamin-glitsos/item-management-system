import io.getquill._

object UsersDAO {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def upsert(item: Users): Unit = {
        ctx.run(
            quote {
                infix"upsert_user(${lift(item.id)}, ${lift(item.person_id)}, ${lift(item.username)}, ${lift(item.password)})".as[Insert[Any]]
            }
        )
    }

    // implicit val usersInsertMeta = insertMeta[Users](_.id)
    //
    // val users = query[Users]
    //
    // def upsert(item: Users): Unit = {
    //     ctx.run(
    //         quote {
    //             users.update(lift(item));
    //             users.filter(s => users.filter(_.id == item.id).nonEmpty)insert(lift(item));
    //         }
    //     )
    // }

    // def insert(item: Users): Unit = {
    //     ctx.run(
    //         quote {
    //             users.insert(lift(item))
    //         }
    //     )
    // }
    //
    // def update(item: Users): Unit = {
    //     ctx.run(
    //         quote {
    //             users.update(lift(item))
    //         }
    //     )
    // }
}
