import bundles.doobie.database._
import bundles.doobie.database.dc._

import doobie._

object UsersDAO {
    // TODO: make a separate DAO which is count. Then use count and list in the Service to calculate the page numbers
    def count() = {
        run(quote(
            query[UsersList]
                .filter(_.deleted_at.isEmpty) // TODO: can you use composition for these first two lines?
                .count
        ))
    }

    def list(pageNumber: Int, pageLength: Int) = {
        run(quote(
            query[UsersList]
                .filter(_.deleted_at.isEmpty)
                .sortBy(x => (x.edited_at, x.created_at))(Ord.descNullsLast)
                // .drop((lift(pageNumber) - 1) * lift(pageLength))
                // .take(lift(pageLength))
        ))
    }

    // def create(user: User) = {
    //     run(quote(
    //         query[User].insert(
    //             _.record_id -> lift(user.record_id),
    //             _.staff_id -> lift(user.staff_id),
    //             _.username -> lift(user.username),
    //             _.password -> lift(user.password)
    //         ).returningGenerated(_.id)
    //     ))
    // }
    //
    // def edit(u: User) = {
    //     run(quote(
    //         query[User]
    //             .filter(x => x.record_id == lift(u.record_id))
    //             .update(
    //                 _.username -> lift(u.username),
    //                 _.password -> lift(u.password))
    //             .returning(_.id)
    //     ))
    // }
    //
    // def open(username: String) = {
    //     run(quote(
    //         query[User].filter(_.username == lift(username))
    //     )).map(_.head)
    // }
    //
    // def permanentlyDelete(username: String) = {
    //     run(quote(
    //         query[User]
    //             .filter(_.username == lift(username))
    //             .delete
    //             // TODO: delete doesnt work. Is this because doobie overrides the import from quill?
    //     ))
    // }
    //
    // def populateAllStaffIds() = {
    //     run(quote(
    //         query[User].update(x => x.staff_id -> x.id)
    //     ))
    // }
}
