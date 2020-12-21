import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import doobie.implicits._
import bundles.doobie.connection._

object UsersServices {
  implicit val rwLocalDateTime: ReadWriter[LocalDateTime] =
    readwriter[ujson.Value].bimap[LocalDateTime](
        x => x.toString(),
        json => {
          val defaultFormat: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
          LocalDateTime.parse(json.toString(), defaultFormat)
        }
    )
  implicit val rwUsersList: ReadWriter[UsersList] = macroRW
  implicit val rwUserOpen: ReadWriter[UsersOpen]  = macroRW

  def list(entityBody: String): String = {
    val body: ujson.Value = ujson.read(entityBody)
    val pageNumber        = body("page_number").num.toInt
    val pageLength        = body("page_length").num.toInt

    (for {
      totalItems <- UsersDAO.count().map(_.toInt)

      val offset     = (pageNumber - 1) * pageLength
      val totalPages = Math.ceil(totalItems.toFloat / pageLength).toInt
      val rangeStart = 1 + offset
      val rangeEnd   = rangeStart + pageLength - 1

      data <- UsersDAO.list(offset, pageLength)

      val output = write(
          ujson.Obj(
              "total_items" -> ujson.Num(totalItems),
              "total_pages" -> ujson.Num(totalPages),
              "range_start" -> ujson.Num(rangeStart),
              "range_end"   -> ujson.Num(rangeEnd),
              "data"        -> writeJs(data)
          )
      )

    } yield (output))
      .transact(xa)
      .unsafeRunSync
  }

  def open(username: String) = {
    (for {
      data <- UsersDAO.open(username)

      val output = write(
          ujson.Obj(
              "data" -> writeJs(data)
          )
      )

    } yield (output))
      .transact(xa)
      .unsafeRunSync
  }
}
