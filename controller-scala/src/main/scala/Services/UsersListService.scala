import java.time.LocalDateTime
import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import doobie._
import cats.implicits._
import upickle.default._
import upickle_bundle.general._

trait UsersListService extends ListServiceTrait {
  final def list(
      pageNumber: Int,
      pageLength: Int,
      search: Option[String]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        val offset: Int = (pageNumber - 1) * pageLength

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

          val totalItemsCount: Int = getOrZero(dataAfterPossibleSeeding.head._1)

          val totalPagesCount: Int = calculatePageCount(
            pageLength,
            totalItemsCount
          )

          val filteredItemsCount: Int = getOrZero(
            dataAfterPossibleSeeding.head._2
          )

          val filteredPagesCount: Int = calculatePageCount(
            pageLength,
            filteredItemsCount
          )

          val pageItemsStart: Int = getOrZero(dataAfterPossibleSeeding.head._3)

          val pageItemsEnd: Int = getOrZero(dataAfterPossibleSeeding.head._4)

          val pageItemsCount: Int = dataAfterPossibleSeeding.length

          val items: List[UsersList] = dataAfterPossibleSeeding map {
            case (
                  totalItemsCount: Int,
                  filteredItemsCount: Int,
                  pageItemsStart: Int,
                  pageItemsEnd: Int,
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
                "total_items_count"    -> ujson.Num(totalItemsCount),
                "total_pages_count"    -> ujson.Num(totalPagesCount),
                "filtered_items_count" -> ujson.Num(filteredItemsCount),
                "filtered_pages_count" -> ujson.Num(filteredPagesCount),
                "page_items_count"     -> ujson.Num(pageItemsCount),
                "page_items_start"     -> ujson.Num(pageItemsStart),
                "page_items_end"       -> ujson.Num(pageItemsEnd),
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
