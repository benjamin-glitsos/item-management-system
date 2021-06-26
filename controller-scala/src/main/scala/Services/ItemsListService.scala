import java.util.Date
import java.time.LocalDateTime
import java.sql.SQLException
import upickle.default._
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ItemsListService extends ListServiceTrait {
  final def list(
      pageNumber: Int,
      pageLength: Int,
      search: Option[String],
      sort: Sort
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
              val totalItemsCount: Int    = dataFirstRow._1
              val filteredItemsCount: Int = dataFirstRow._2
              val pageItemsStart: Int     = dataFirstRow._3
              val pageItemsEnd: Int       = dataFirstRow._4

              val items: List[ItemsList] = data.map(x => {
                val sku: String                 = x._5
                val name: String                = x._6
                val description: Option[String] = x._7
                val acquisitionDate: Date       = DateUtilities.parse(x._8)
                val createdAt: LocalDateTime =
                  LocalDateTimeUtilities.parse(x._9)
                val editedAt: Option[LocalDateTime] =
                  x._10.map(LocalDateTimeUtilities.parse(_))

                ItemsList(
                  sku,
                  name,
                  description,
                  acquisitionDate,
                  createdAt,
                  editedAt
                )
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

          a <- reseedIfNeeded(
            totalItemsCount,
            search,
            ItemsSeeder.apply
          )

        } yield (output))
          .transact(transactor)
          .unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
