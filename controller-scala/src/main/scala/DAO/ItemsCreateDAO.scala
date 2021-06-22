import doobie_import.database.dc._

trait ItemsCreateDAO {
  final def create(
      sku: String,
      name: String,
      description: Option[String],
      additionalNotes: Option[String]
  ) = {
    run(
      quote(
        query[ItemsOpen]
          .insert(
            _.sku              -> lift(sku),
            _.name             -> lift(name),
            _.description      -> lift(description),
            _.additional_notes -> lift(additionalNotes)
          )
      )
    )
  }
}
