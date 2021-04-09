import doobie_bundle.database.dc._

trait ItemsCreateDAO {
  final def create(
      key: String,
      name: String,
      description: Option[String],
      additionalNotes: Option[String]
  ) = {
    run(
      quote(
        query[ItemsWithMeta]
          .insert(
            _.key         -> lift(key),
            _.name        -> lift(name),
            _.description -> lift(description),
            _.additionalNotes       -> lift(additionalNotes)
          )
      )
    )
  }
}
