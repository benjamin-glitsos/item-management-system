import doobie_import.database.dc._

trait ItemsDeleteDAO {
  final def softDelete(keys: List[String]) = {
    run(
      quote(
        query[ItemsOpen]
          .filter(x => liftQuery(keys.toSet).contains(x.key))
          .update(_.is_deleted -> lift(true))
      )
    )
  }

  final def restoreDelete(keys: List[String]) = {
    run(
      quote(
        query[ItemsOpen]
          .filter(x => liftQuery(keys.toSet).contains(x.key))
          .update(_.is_deleted -> lift(false))
      )
    )
  }

  final def hardDelete(keys: List[String]) = {
    run(
      quote(
        query[ItemsOpen]
          .filter(x => liftQuery(keys.toSet).contains(x.key))
          .delete
      )
    )
  }

  final def hardDeleteAllRows() = {
    run(
      quote(
        query[ItemsOpen].delete
      )
    )
  }
}
