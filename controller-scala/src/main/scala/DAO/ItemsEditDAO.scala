import doobie_import.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait ItemsEditDAO {
  final def edit(
      oldSku: String,
      newSku: Option[String],
      upc: Option[String],
      name: Option[String],
      description: Option[Option[String]],
      acquisition_date: Option[String],
      expiration_date: Option[Option[String]],
      unit_cost: Option[String],
      unit_price: Option[Option[String]],
      quantity_available: Option[Int],
      quantity_sold: Option[Int],
      additionalNotes: Option[Option[String]]
  ) = {
    val update: Fragment =
      fr"UPDATE items_open"

    val set: Fragment = setOpt(
      newSku.map(s => fr"sku=$s"),
      upc.map(s => fr"upc=$s"),
      name.map(s => fr"name=$s"),
      description.map(s => fr"description=$s"),
      acquisition_date.map(s => fr"acquisition_date=$s"),
      expiration_date.map(s => fr"expiration_date=$s"),
      unit_cost.map(s => fr"unit_cost=$s"),
      unit_price.map(s => fr"unit_price=$s"),
      quantity_available.map(s => fr"quantity_available=$s"),
      quantity_sold.map(s => fr"quantity_sold=$s"),
      additionalNotes.map(s => fr"additional_notes=$s")
    )

    val where: Fragment = whereAnd(fr"sku=$oldSku")

    (update ++ set ++ where).update.run
  }
}
