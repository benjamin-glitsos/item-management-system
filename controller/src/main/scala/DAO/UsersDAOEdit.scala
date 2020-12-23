import doobie_bundle.database.dc._
import doobie.Fragments
import doobie.Fragments.{setOpt}

// TODO: https://javadoc.io/doc/org.tpolecat/doobie-core_2.12/latest/doobie/util/fragments$.html

trait UsersDAOEdit {
  def edit(
      oldUsername: String,
      newUsername: Option[String],
      password: Option[String],
      emailAddress: Option[String],
      notes: Option[String]
  ) = {
    val usernameFr: Fragment = newUsername.map(s => fr"username == $s")
    sql"UPDATE users_with_meta ${setOpt(usernameFr)} WHERE username = '$oldUsername'".update
  }
}
// _.username,
// username,
// _.password,
// password,
// _.email_address,
// email_address,
// _.notes,
// notes
