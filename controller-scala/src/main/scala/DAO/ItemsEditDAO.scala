import doobie_import.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait ItemsEditDAO {
  final def edit(
      oldSku: String,
      newSku: Option[String],
      name: Option[String],
      description: Option[Option[String]],
      additionalNotes: Option[Option[String]]
  ) = {
    val update: Fragment =
      fr"UPDATE items_open"

    val set: Fragment = setOpt(
      newSku.map(s => fr"sku=$s"),
      name.map(s => fr"name=$s"),
      description.map(s => fr"description=$s"),
      additionalNotes.map(s => fr"additional_notes=$s")
    )

    val where: Fragment = whereAnd(fr"sku=$oldSku")

    (update ++ set ++ where).update.run
  }
}
