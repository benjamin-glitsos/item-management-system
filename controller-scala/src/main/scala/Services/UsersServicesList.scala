import java.time.LocalDateTime
import scala.util.Try
import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import doobie._
import cats.implicits._
import upickle.default._
import upickle_bundle.general._

trait UsersServicesList {
  private final def calculatePageCount(pageLength: Int, items: Int): Int = {
    Math.ceil(items.toFloat / pageLength).toInt
  }

  private final def getOrZero(n: Int): Int = Try(n).toOption.getOrElse(0)

  final def list(
      pageNumber: Int,
      pageLength: Int,
      search: Option[String]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        val offset: Int     = (pageNumber - 1) * pageLength
        val rangeStart: Int = 1 + offset
        val rangeEnd: Int   = rangeStart + pageLength - 1

        (for {
          data <- UsersDAO.list(offset, pageLength, search)

          dataAfterPossibleSeeding <- {
            if (
              getOrZero(data.head._1) <= 10 && search.isEmpty && System
                .getenv("PROJECT_MODE") != "production"
            ) {
              UsersSeeder()
              UsersDAO.list(offset, pageLength, search)
            } else {
              data.pure[ConnectionIO]
            }
          }

          val totalItems: Int = getOrZero(dataAfterPossibleSeeding.head._1)
          val totalPages: Int = calculatePageCount(pageLength, totalItems)
          val totalFilteredItems: Int = getOrZero(
            dataAfterPossibleSeeding.head._2
          )
          val totalFilteredPages: Int = calculatePageCount(
            pageLength,
            totalFilteredItems
          )

          val items: List[UsersList] = dataAfterPossibleSeeding map {
            case (
                  totalItems: Int,
                  totalFilteredItems: Int,
                  username: String,
                  email_address: String,
                  first_name: String,
                  last_name: String,
                  other_names: Option[String],
                  created_at: LocalDateTime,
                  edited_at: Option[LocalDateTime]
                ) =>
              UsersList(
                username,
                email_address,
                first_name,
                last_name,
                other_names,
                created_at,
                edited_at
              )
          }

          val output: String = write(
            ujson.Obj(
              "data" -> ujson.Obj(
                "page_number"          -> ujson.Num(pageNumber),
                "page_length"          -> ujson.Num(pageLength),
                "total_items"          -> ujson.Num(totalItems),
                "total_pages"          -> ujson.Num(totalPages),
                "total_filtered_items" -> ujson.Num(totalFilteredItems),
                "total_filtered_pages" -> ujson.Num(totalFilteredPages),
                "range_start"          -> ujson.Num(rangeStart),
                "range_end"            -> ujson.Num(rangeEnd),
                "items"                -> writeJs(items)
              )
            )
          )
        } yield (output))
          .transact(xa)
          .unsafeRunSync
      } catch {
        case e: java.sql.SQLException =>
          System.err.println(e.getMessage)
          System.err.println(e.getSQLState)
          new String
      }
    )
  }
}
