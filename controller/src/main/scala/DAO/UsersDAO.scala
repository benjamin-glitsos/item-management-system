import bundles.doobie.database._
import bundles.doobie.database.dc._

import doobie._
import cats.data.ValidatedNel

object UsersDAO {
    val name = sys.env.getOrElse("USERS_TABLE", "users")

    def getRecord(username: String) = {
        run(quote(
            query[User].filter(x => x.username == lift(username)).map(x => x.record_id)
        ))
    }

    def create(u: User) = {
        run(quote(
            query[User].insert(
                _.record_id -> lift(u.record_id),
                _.staff_id -> lift(u.staff_id),
                _.username -> lift(u.username),
                _.password -> lift(u.password)
            )
        ))
    }

    def edit(u: User) = {
        run(quote(
            query[User]
                .filter(x => x.record_id == lift(u.record_id))
                .update(
                    _.username -> lift(u.username),
                    _.password -> lift(u.password)
                )
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

    def open(username: String): ConnectionIO[ValidatedNel[String, User]] = {
        run(quote(
            query[User].filter(_.username == lift(username))
        )).map(UserValidators.userExists(_))
    }

    // def delete(username: String, user_id: Int) = {
    //     run(quote(
    //         for {
    //             r_id <- getRecord(username)
    //             _ <- RecordsDAO.delete(
    //                 id = r_id.head,
    //                 user_id
    //             )
    //         } yield ()
    //     ))
    // }

    // def restore(username: String, user_id: Int) = {
    //     run(quote(
    //         for {
    //             r_id <- getRecord(username)
    //             _ <- RecordsDAO.restore(
    //                 id = r_id.head,
    //                 user_id
    //             )
    //         } yield ()
    //     ))
    // }

    def permanentlyDelete(username: String) = {
        run(quote(
            query[User].filter(_.username == lift(username)).delete
        ))
    }

    def populateAllStaffIds() = {
        run(quote(
            query[User].update(x => x.staff_id -> x.id)
        ))
    }
}
