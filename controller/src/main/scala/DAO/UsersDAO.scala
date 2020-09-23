import bundles.doobie.database._
import bundles.doobie.database.dc._

import doobie._

object UsersDAO {
    val name = sys.env.getOrElse("USERS_TABLE", "users")

    def create(user: User) = {
        run(quote(
            query[User].insert(
                _.record_id -> lift(user.record_id),
                _.staff_id -> lift(user.staff_id),
                _.username -> lift(user.username),
                _.password -> lift(user.password)
            ).returningGenerated(_.id)
        ))
    }

    def edit(u: User) = {
        run(quote(
            query[User]
                .filter(x => x.record_id == lift(u.record_id))
                .update(
                    _.username -> lift(u.username),
                    _.password -> lift(u.password))
                .returning(_.id)
        ))
    }

    def list(p: Page) = {
        run(quote(
            (for {
                u <- query[User]
                r <- query[Record]
                    .join(_.id == u.record_id)
                    .filter(_.deleted_at.isEmpty)
                creator <- query[User].join(_.id == r.created_by)
                editor <- query[User].leftJoin(x => r.edited_by.exists(_ == x.id))
            } yield (UserList(
                    username = u.username,
                    edited_at = r.edited_at,
                    edited_by = editor.map(_.username),
                    created_at = r.created_at,
                    created_by = creator.username
                )))
                    .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
                    .drop((lift(p.number) - 1) * lift(p.length))
                    .take(lift(p.length))
        ))
    }

    def open(username: String) = {
        run(quote(
            query[User].filter(_.username == lift(username))
        )).map(_.head)
    }

    def delete(username: String, user_username: String) = {
        run(quote(
            for {
                r <- open(username)
                u <- open(user_username)
                _ <- RecordsDAO.delete(
                    id = r.id.head,
                    user_id = u.id.head
                )
            } yield ()
        ))
    }

    def restore(username: String, user_username: String) = {
        run(quote(
            for {
                r <- open(username)
                u <- open(user_username)
                _ <- RecordsDAO.restore(
                    id = r.id.head,
                    user_id = u.id.head
                )
            } yield ()
        ))
    }

    def permanentlyDelete(username: String) = {
        run(quote(
            query[User]
                .filter(_.username == lift(username))
                .delete
                // .returning(_.id)
        ))
    }

    def populateAllStaffIds() = {
        run(quote(
            query[User].update(x => x.staff_id -> x.id)
        ))
    }
}
