import doobie_import.database.dc._

trait ItemsCreateDAO {
  final def create(
      key: String,
      name: String,
      description: Option[String],
      additionalNotes: Option[String]
  ) = {
    run(
      quote(
        query[ItemsOpen]
          .insert(
            _.key              -> lift(key),
            _.name             -> lift(name),
            _.description      -> lift(description),
            _.additional_notes -> lift(additionalNotes)
          )
      )
    )
  }
}
