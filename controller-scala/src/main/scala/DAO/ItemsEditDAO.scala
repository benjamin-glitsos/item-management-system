import java.util.Date
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait ItemsEditDAO extends DoobieDatabaseMixin {
  final def edit(
      oldSku: String,
      newSku: Option[String],
      upc: Option[String],
      name: Option[String],
      description: Option[Option[String]],
      acquisitionDate: Option[Date],
      expirationDate: Option[Option[Date]],
      unitCost: Option[Double],
      unitPrice: Option[Option[Double]],
      quantityAvailable: Option[Int],
      quantitySold: Option[Int],
      additionalNotes: Option[Option[String]]
  ) = {
    val update: Fragment =
      fr"UPDATE items_open"

    val set: Fragment = setOpt(
      newSku.map(s => fr"sku=$s"),
      upc.map(s => fr"upc=$s"),
      name.map(s => fr"name=$s"),
      description.map(s => fr"description=$s"),
      acquisitionDate.map(s => fr"acquisition_date=$s"),
      expirationDate.map(s => fr"expiration_date=$s"),
      unitCost.map(s => fr"unit_cost=$s"),
      unitPrice.map(s => fr"unit_price=$s"),
      quantityAvailable.map(s => fr"quantity_available=$s"),
      quantitySold.map(s => fr"quantity_sold=$s"),
      additionalNotes.map(s => fr"additional_notes=$s")
    )

    val where: Fragment = whereAnd(fr"sku=$oldSku")

    val returning: Fragment = fr"""
    RETURNING sku
            , upc
            , name
            , description
            , acquisition_date
            , expiration_date
            , unit_cost
            , unit_price
            , quantity_available
            , quantity_sold
            , additional_notes
            , metakey
            , opens
            , edits
            , is_deleted
            , created_at
            , created_by
            , edited_at
            , edited_by
            , deleted_at
            , deleted_by
    """

    (update ++ set ++ where ++ returning).query[ItemsOpen].to[List]
  }
}
