import doobie_import.database.dc._

trait ItemsCreateDAO {
  final def create(
      sku: String,
      upc: String,
      name: String,
      description: Option[String],
      acquisition_date: String,
      expiration_date: String,
      unit_cost: String,
      unit_price: String,
      quantity_available: Int,
      quantity_sold: Int,
      additionalNotes: Option[String]
  ) = {
    run(
      quote(
        query[ItemsOpen]
          .insert(
            _.sku                -> lift(sku),
            _.upc                -> lift(upc),
            _.name               -> lift(name),
            _.description        -> lift(description),
            _.acquisition_date   -> lift(acquisition_date),
            _.expiration_date    -> lift(expiration_date),
            _.unit_cost          -> lift(unit_cost),
            _.unit_price         -> lift(unit_price),
            _.quantity_available -> lift(quantity_available),
            _.quantity_sold      -> lift(quantity_sold),
            _.additional_notes   -> lift(additionalNotes)
          )
      )
    )
  }
}
