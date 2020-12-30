import doobie_bundle.database.dc._

trait UsersDAOCount {
  final def count() = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(!_.is_deleted)
          .size
      )
    )
  }
}
