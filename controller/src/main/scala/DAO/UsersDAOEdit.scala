import doobie_bundle.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait UsersDAOEdit {
  def edit(
      oldUsername: String,
      newUsername: Option[String],
      password: Option[String],
      emailAddress: Option[String],
      notes: Option[String]
  ) = {
    val updateFr: Fragment =
      fr"UPDATE users_with_meta"

    val setFr: Fragment = setOpt(
      newUsername.map(s => fr"username = $s"),
      password.map(s => fr"password = $s"),
      emailAddress.map(s => fr"email_address = $s"),
      notes.map(s => fr"notes = $s")
    )

    val whereFr: Fragment = whereAnd(fr"username = $oldUsername")

    val queryFr = updateFr ++ setFr ++ whereFr

    queryFr.update.run
  }
}
