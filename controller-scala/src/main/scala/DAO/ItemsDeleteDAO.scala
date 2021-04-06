import doobie_bundle.database.dc._

trait ItemsDeleteDAO {
  final def softDelete(keys: List[String]) = {
    run(
      quote(
        query[ItemsWithMeta]
          .filter(x => liftQuery(keys.toSet).contains(x.key))
          .update(_.is_deleted -> lift(true))
      )
    )
  }

  final def restoreDelete(keys: List[String]) = {
    run(
      quote(
        query[ItemsWithMeta]
          .filter(x => liftQuery(keys.toSet).contains(x.key))
          .update(_.is_deleted -> lift(false))
      )
    )
  }

  final def hardDelete(keys: List[String]) = {
    run(
      quote(
        query[ItemsWithMeta]
          .filter(x => liftQuery(keys.toSet).contains(x.key))
          .delete
      )
    )
  }

  final def hardDeleteAllRows() = {
    run(
      quote(
        query[ItemsWithMeta].delete
      )
    )
  }
}
