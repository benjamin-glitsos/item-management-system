import java.util.Date

trait ItemsCreateDAO extends DoobieDatabaseMixin {
  import dc._

  final def create(
      sku: String,
      upc: String,
      name: String,
      description: Option[String],
      acquisitionDate: Date,
      expirationDate: Option[Date],
      unitCost: Double,
      unitPrice: Option[Double],
      quantityAvailable: Int,
      quantitySold: Int,
      additionalNotes: Option[String]
  ) = run(
    quote(
      query[ItemsOpen]
        .insert(
          _.sku                -> lift(sku),
          _.upc                -> lift(upc),
          _.name               -> lift(name),
          _.description        -> lift(description),
          _.acquisition_date   -> lift(acquisitionDate),
          _.expiration_date    -> lift(expirationDate),
          _.unit_cost          -> lift(unitCost),
          _.unit_price         -> lift(unitPrice),
          _.quantity_available -> lift(quantityAvailable),
          _.quantity_sold      -> lift(quantitySold),
          _.additional_notes   -> lift(additionalNotes)
        )
    )
  )
}
