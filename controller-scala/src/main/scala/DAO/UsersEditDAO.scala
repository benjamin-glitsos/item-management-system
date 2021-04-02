import doobie_bundle.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait UsersEditDAO {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      emailAddress: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[String],
      password: Option[String],
      notes: Option[String]
  ) = {
    val update: Fragment =
      fr"UPDATE users_with_meta"

    val set: Fragment = setOpt(
      newUsername.map(s => fr"username=$s"),
      emailAddress.map(s => fr"email_address=$s"),
      firstName.map(s => fr"first_name=$s"),
      lastName.map(s => fr"last_name=$s"),
      otherNames.map(s => fr"other_names=$s"),
      password.map(s => fr"password=$s"),
      notes.map(s => fr"notes=$s")
    )

    val where: Fragment = whereAnd(fr"username=$oldUsername")

    (update ++ set ++ where).update.run
  }
}
