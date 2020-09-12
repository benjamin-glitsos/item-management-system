object UsersServices {
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

    def open(username: String, user_id: Int) = {
        for {
          u <- UsersDAO.open(username)

          s <- StaffDAO.summary(u.staff_id)

          r <- RecordsDAO.open(
              id = u.record_id,
              u.user_id
          )

          _ <- RecordsDAO.opened(
              id = u.record_id,
              u.user_id
          )



          userOpen = Some(
              UserOpen(
                  user = u,
                  relations = List(s),
                  record = r
              )
          )

          // userOpen = u match {
          //     case None => None
          //     case Some(u) => for {
          //     } yield (
          //         Some(
          //             UserOpen(
          //                 user = u,
          //                 relations = List(s),
          //                 record = r
          //             )
          //         )
          //     )
          // }
        } yield (userOpen)
    }

    def delete(username: String, user_id: Int) = {
        UsersDAO.delete(username, user_id)
    }

    def restore(username: String, user_id: Int) = {
        UsersDAO.restore(username, user_id)
    }

    def permanentlyDelete(username: String) = {
        UsersDAO.permanentlyDelete(username)
    }
}
