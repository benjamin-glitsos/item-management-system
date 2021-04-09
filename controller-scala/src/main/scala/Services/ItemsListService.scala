import java.time.LocalDateTime
import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import doobie._
import cats.implicits._
import upickle.default._
import upickle_bundle.general._

trait ItemsListService extends ListServiceTrait {
  final def list(
      pageNumber: Int,
      pageLength: Int,
      search: Option[String],
      sort: (String, String)
  ): ujson.Value = {
    read[ujson.Value](
      try {
        val offset: Int = calculateOffset(pageNumber, pageLength)

        (for {
          data <- UsersDAO.list(offset, pageLength, search, sort)

          val (
            totalItemsCount: Int,
            filteredItemsCount: Int,
            pageItemsStart: Int,
            pageItemsEnd: Int,
            items: List[ItemsList]
          ) = data.headOption match {
            case None => (0, 0, 0, 0, List());
            case Some(dataFirstRow) => {
              val totalItemsCount    = dataFirstRow._1
              val filteredItemsCount = dataFirstRow._2
              val pageItemsStart     = dataFirstRow._3
              val pageItemsEnd       = dataFirstRow._4

              val items: List[ItemsList] = data.map(x => {
                val key         = x._5
                val name        = x._6
                val description = x._7
                val created_at  = x._8
                val edited_at   = x._9

                ItemsList(key, name, description, created_at, edited_at)
              })

              (
                totalItemsCount,
                filteredItemsCount,
                pageItemsStart,
                pageItemsEnd,
                items
              )
            };
          }

          val totalPagesCount: Int = calculatePageCount(
            pageLength,
            totalItemsCount
          )

          val filteredPagesCount: Int = calculatePageCount(
            pageLength,
            filteredItemsCount
          )

          val pageItemsCount: Int = items.length

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
                "page_number"          -> ujson.Num(pageNumber),
                "page_length"          -> ujson.Num(pageLength),
                "items"                -> writeJs(items)
              )
            )
          )

          reseedIfNeeded <- {
            if (
              LogicUtilities.all(
                List(
                  totalItemsCount <= 15,
                  totalItemsCount != 0,
                  search.isEmpty,
                  System
                    .getenv("PROJECT_MODE") != "production"
                )
              )
            ) {
              ItemsSeeder().pure[ConnectionIO]
            } else {
              ().pure[ConnectionIO]
            }
          }

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
