trait ItemsDeleteDAO extends DoobieDatabaseMixin {
  import dc._

  final def softDelete(skus: List[String]) = run(
    quote(
      query[ItemsOpen]
        .filter(x => liftQuery(skus.toSet).contains(x.sku))
        .update(_.is_deleted -> lift(true))
    )
  )

  final def restoreDelete(skus: List[String]) = run(
    quote(
      query[ItemsOpen]
        .filter(x => liftQuery(skus.toSet).contains(x.sku))
        .update(_.is_deleted -> lift(false))
    )
  )

  final def hardDelete(skus: List[String]) = run(
    quote(
      query[ItemsOpen]
        .filter(x => liftQuery(skus.toSet).contains(x.sku))
        .delete
    )
  )

  final def hardDeleteAllRows() = run(
    quote(
      query[ItemsOpen].delete
    )
  )
}
