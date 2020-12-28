import doobie_bundle.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait UsersDAOEdit {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      password: Option[String],
      emailAddress: Option[String],
      notes: Option[String]
  ) = {
    val update: Fragment =
      fr"UPDATE users_with_meta"

    val set: Fragment = setOpt(
      newUsername.map(s => fr"username=$s"),
      password.map(s => fr"password=$s"),
      emailAddress.map(s => fr"email_address=$s"),
      notes.map(s => fr"notes=$s")
    )

    val where: Fragment = whereAnd(fr"username=$oldUsername")

    (update ++ set ++ where).update.run
  }
}
