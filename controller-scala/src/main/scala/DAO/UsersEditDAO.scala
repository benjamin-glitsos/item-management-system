import doobie_import.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait UsersEditDAO {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[Option[String]],
      emailAddress: Option[String],
      password: Option[String],
      additionalNotes: Option[Option[String]]
  ) = {
    val update: Fragment =
      fr"UPDATE users_with_meta"

    val set: Fragment = setOpt(
      newUsername.map(s => fr"username=$s"),
      firstName.map(s => fr"first_name=$s"),
      lastName.map(s => fr"last_name=$s"),
      otherNames.map(s => fr"other_names=$s"),
      emailAddress.map(s => fr"email_address=$s"),
      password.map(s => fr"password=$s"),
      additionalNotes.map(s => fr"additional_notes=$s")
    )

    val where: Fragment = whereAnd(fr"username=$oldUsername")

    (update ++ set ++ where).update.run
  }
}
