import java.sql.SQLException
import doobie.implicits._
import cats.implicits._
import upickle.default._

trait UsersListService
    extends ListServiceMixin
    with DoobieConnectionMixin
    with UpickleMixin {
  private final def formatName(
      firstName: String,
      lastName: String,
      otherNames: Option[String]
  ): String = {
    otherNames match {
      case None => List(firstName, lastName).mkString(" ")
      case Some(otherNames) => {
        val otherNamesInitials =
          otherNames.split(" ").filter(_.length >= 1).map(_.charAt(0) + ".")
        (List(firstName) ++ otherNamesInitials ++ List(lastName))
          .mkString(" ")
      }

    }
  }

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
          data <- UsersDAO.list(offset, pageLength, search, sort)

          val (
            totalItemsCount: Int,
            filteredItemsCount: Int,
            pageItemsStart: Int,
            pageItemsEnd: Int,
            items: List[UsersList]
          ) = data.headOption match {
            case None => emptyListData[UsersList]();
            case Some(dataFirstRow) => {
              val totalItemsCount: Int    = dataFirstRow._1
              val filteredItemsCount: Int = dataFirstRow._2
              val pageItemsStart: Int     = dataFirstRow._3
              val pageItemsEnd: Int       = dataFirstRow._4

              val items: List[UsersList] = data.map(x => {
                val username: String           = x._5
                val emailAddress: String       = x._6
                val firstName: String          = x._7
                val lastName: String           = x._8
                val otherNames: Option[String] = x._9
                val createdAt: String          = x._10
                val editedAt: Option[String]   = x._11
                val name: String               = formatName(firstName, lastName, otherNames)

                UsersList(username, name, emailAddress, createdAt, editedAt)
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
            UsersSeeder.apply
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
