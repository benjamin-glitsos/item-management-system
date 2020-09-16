import doobie._
import cats.data.Validated.{Invalid, Valid}

object UsersServices extends ValidationUtilities {
    def create(u: User, user_id: Int, notes: Option[String]) = {
        for {
          r_id <- RecordsDAO.create(user_id, notes)
          _ <- UsersDAO.create(u.copy(record_id = r_id))
        } yield ()
    }

    def edit(u: User, user_id: Int, notes: Option[String]) = {
        for {
          _ <- RecordsDAO.edit(
              id = u.record_id,
              user_id,
              notes
          )
          _ <- UsersDAO.edit(u)
        } yield ()
    }

    def list(maybeNumber: Option[Int], maybeLength: Option[Int]) = {
        val number = maybeNumber.getOrElse(1)
        val length = maybeLength.getOrElse(25)

        UsersDAO.list(Page(number, length))
    }

    def open(username: String, user_id: Int): ConnectionIO[Validation[User]] = {
        for {
          u <- UsersDAO.open(username)

          // val x = u match {
          //     case Valid(u) => for {
          //         s <- StaffDAO.summary(u.staff_id)
          //
          //         r <- RecordsDAO.open(
          //             id = u.record_id,
          //             u.id
          //         )
          //
          //         _ <- RecordsDAO.opened(
          //             id = u.record_id,
          //             u.id
          //         )
          //     } yield (Valid(UserOpen(
          //         user = u,
          //         relations = List(s.head),
          //         record = r.head
          //     )))
          //     case Invalid(es) => Invalid(es)
          //     // TODO: make mapping over this Validated type work
          // }
        } yield (u)
    }

    // def delete(username: String, user_id: Int) = {
    //     UsersDAO.delete(username, user_id)
    // }
    //
    // def restore(username: String, user_id: Int) = {
    //     UsersDAO.restore(username, user_id)
    // }

    def permanentlyDelete(username: String) = {
        UsersDAO.permanentlyDelete(username)
    }
}
