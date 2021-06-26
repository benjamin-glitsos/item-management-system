import doobie_import.database.dc._
import doobie_import.database._

trait ItemsOpenDAO {
  final def open(sku: String) = {
    run(
      quote(
        query[ItemsOpen].filter(_.sku == lift(sku))
      )
    ).map(_.head)
  }

  final def incrementOpens(sku: String) = {
    run(
      quote(
        query[ItemsOpen]
          .filter(_.sku == lift(sku))
          .update(x => x.opens -> (x.opens + 1))
      )
    )
  }
}
