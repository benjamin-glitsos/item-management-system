import doobie._
import cats.data.Validated.{Invalid, Valid}
// import io.circe.generic.auto._, io.circe.syntax._
import io.circe.Json

object UsersServices {
    def list(pageNumber: Int, pageLength: Int): ConnectionIO[Validation[Json]] = {
        for {
            totalItems <- UsersDAO.count().map(_.toInt)

            val totalPages = Math.ceil(totalItems.toFloat / pageLength).toInt
            val offset = (pageNumber - 1) * pageLength
            val rangeStart = 1 + offset
            val rangeEnd = rangeStart + pageLength - 1

            data: UsersList <- UsersDAO.list(offset, pageLength)

            val output = validatePageRange[Json](
                rangeStart,
                rangeEnd,
                totalItems,
                Json.object(
                    "total_items" -> Json(totalItems).fromInt,
                    "total_pages" -> Json(totalPages).fromInt,
                    "range_start" -> Json(rangeStart).fromInt,
                    "range_end" -> Json(rangeEnd).fromInt,
                    "data" -> Json(data).as[Json]
                )
            )

        } yield (output)
    }

    // def create(user: User, user_username: String, notes: Option[String]): ConnectionIO[RecordResponse] = {
    //     for {
    //       u <- UsersDAO.open(user_username)
    //       r <- RecordsDAO.create(user_id = u.id, notes)
    //       _ <- UsersDAO.create(user.copy(record_id = r.id))
    //     } yield (RecordResponse(r.uuid))
    // }
    //
    // def edit(u: User, user_username: String, notes: Option[String]): ConnectionIO[RecordResponse] = {
    //     for {
    //         u <- UsersDAO.open(user_username)
    //         r <- RecordsDAO.edit(
    //             id = u.record_id,
    //             user_id = u.id,
    //             notes
    //         )
    //         _ <- UsersDAO.edit(u)
    //     } yield (RecordResponse(r.uuid))
    // }

    // def open(username: String, user_username: String): ConnectionIO[UserOpen] = {
    //     for {
    //       u <- UsersDAO.open(username)
    //       s <- StaffDAO.summary(u.staff_id)
    //       r <- RecordsDAO.open(id = u.record_id)
    //       _ <- RecordsDAO.opened(
    //           id = u.record_id,
    //           user_id = u.id
    //       )
    //     } yield (UserOpen(
    //         user = u,
    //         relations = List(s),
    //         record = r
    //     ))
    // }
    //
    // def delete(username: String, user_username: String): ConnectionIO[RecordResponse] = {
    //     for {
    //         u <- UsersDAO.open(username)
    //         e <- UsersDAO.open(user_username)
    //         r <- RecordsDAO.delete(
    //             id = u.record_id,
    //             user_id = e.id
    //         )
    //     } yield (RecordResponse(r.uuid))
    // }
    //
    // def restore(username: String, user_username: String): ConnectionIO[RecordResponse] = {
    //     for {
    //         u <- UsersDAO.open(username)
    //         e <- UsersDAO.open(user_username)
    //         r <- RecordsDAO.restore(
    //             id = u.record_id,
    //             user_id = e.id
    //         )
    //     } yield (RecordResponse(r.uuid))
    // }
    //
    // def permanentlyDelete(username: String): ConnectionIO[RecordResponse] = {
    //     for {
    //         u <- UsersDAO.open(username)
    //         r <- RecordsDAO.openBasic(u.record_id)
    //         _ <- UsersDAO.permanentlyDelete(username)
    //         _ <- RecordsDAO.permanentlyDelete(u.record_id)
    //     } yield (RecordResponse(r.uuid))
    // }
}
