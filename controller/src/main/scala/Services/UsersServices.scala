import doobie._
import cats.data.Validated.{Invalid, Valid}

object UsersServices {
    def create(user: User, user_username: String, notes: Option[String]): ConnectionIO[CreateResponse] = {
        for {
          u <- UsersDAO.open(user_username)
          r <- RecordsDAO.create(user_id = u.head.id, notes)
          _ <- UsersDAO.create(user.copy(record_id = r.id))
        } yield (CreateResponse(r.uuid))
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

    def open(username: String) = {
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
