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
          data <- ItemsDAO.list(offset, pageLength, search, sort)

          val (
            totalItemsCount: Int,
            filteredItemsCount: Int,
            pageItemsStart: Int,
            pageItemsEnd: Int,
            items: List[ItemsList]
          ) = data.headOption match {
            case None => emptyListData[ItemsList]();
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

          val output: String = createListOutput(
            totalItemsCount,
            filteredItemsCount,
            pageItemsCount = items.length,
            pageItemsStart,
            pageItemsEnd,
            pageNumber,
            pageLength,
            writeJs(items)
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
