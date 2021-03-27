import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import doobie._
import cats.implicits._
import upickle.default._
import upickle_bundle.general._

trait UsersServicesList {
  final def list(
      pageNumber: Int,
      pageLength: Int,
      search: Option[String]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          totalItems <- UsersDAO.count().map(_.toInt)
          _ <- {
            if (
              totalItems <= 10 && System.getenv("PROJECT_MODE") != "production"
            ) { UsersSeeder() }
            ().pure[ConnectionIO]
          }

          data <- UsersDAO.list(offset, pageLength, search)

          // val totalItems: Int = data.length
          val offset: Int     = (pageNumber - 1) * pageLength
          val totalPages: Int = Math.ceil(totalItems.toFloat / pageLength).toInt
          val rangeStart: Int = 1 + offset
          val rangeEnd: Int   = rangeStart + pageLength - 1

          val output: String = write(
            ujson.Obj(
              "data" -> ujson.Obj(
                "page_number" -> ujson.Num(pageNumber),
                "page_length" -> ujson.Num(pageLength),
                "total_pages" -> ujson.Num(totalPages),
                "total_items" -> ujson.Num(totalItems),
                "range_start" -> ujson.Num(rangeStart),
                "range_end"   -> ujson.Num(rangeEnd),
                "items"       -> writeJs(data)
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
