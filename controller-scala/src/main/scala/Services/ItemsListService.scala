import java.util.Date
import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait ItemsListService
    extends ListServiceMixin
    with DoobieConnectionMixin
    with UpickleMixin
    with DateMixin {
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
                val acquisitionDate: Date       = x._8
                val createdAt: Int              = x._9
                val editedAt: Option[Int]       = x._10

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

        } yield (output)).transact(transactor).unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
