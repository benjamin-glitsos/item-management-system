import java.sql.SQLException
import doobie.implicits._
import cats.implicits._
import upickle.default._

trait UsersListService
    extends ListServiceMixin
    with DoobieConnectionMixin
    with UpickleMixin
    with StringMixin {
  final def list(
      pageNumber: Int,
      pageLength: Int,
      search: Option[String],
      sort: Sort
  ): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          data <- UsersDAO.list(
            calculateOffset(pageNumber, pageLength),
            pageLength,
            search,
            sort
          )

          val stats: ListStats = data.headOption match {
            case None => ListStats(0, 0, 0, 0)
            case Some(head) =>
              ListStats(
                head.totalCount,
                head.filteredCount,
                head.pageStart,
                head.pageEnd
              )
          }

          val items: List[UsersList] = data.map(x =>
            UsersList(
              x.username,
              formatName(x.firstName, x.lastName, x.otherNames),
              x.emailAddress,
              x.createdAt,
              x.editedAt
            )
          )

          val output: String = createListOutput(
            totalItemsCount = stats.totalCount,
            filteredItemsCount = stats.filteredCount,
            pageItemsCount = items.length,
            pageItemsStart = stats.pageStart,
            pageItemsEnd = stats.pageEnd,
            pageNumber,
            pageLength,
            writeJs(items)
          )

          a <- reseedIfNeeded(
            stats.totalCount,
            search,
            UsersSeeder.apply
          )

        } yield (output)).transact(transactor).unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
