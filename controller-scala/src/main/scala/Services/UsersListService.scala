import doobie.implicits._
import doobie_import.connection._
import doobie._
import cats.implicits._
import upickle.default._
import upickle_import.general._

trait UsersListService extends ListServiceTrait {
  private final def friendlyName(
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
              val totalItemsCount    = dataFirstRow._1
              val filteredItemsCount = dataFirstRow._2
              val pageItemsStart     = dataFirstRow._3
              val pageItemsEnd       = dataFirstRow._4

              val items: List[UsersList] = data.map(x => {
                val username      = x._5
                val email_address = x._6
                val firstName     = x._7
                val lastName      = x._8
                val otherNames    = x._9
                val created_at    = x._10
                val edited_at     = x._11
                val name          = friendlyName(firstName, lastName, otherNames)

                UsersList(username, email_address, name, created_at, edited_at)
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
