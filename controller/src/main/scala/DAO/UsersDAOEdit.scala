import doobie_bundle.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt}
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._

// TODO: https://javadoc.io/doc/org.tpolecat/doobie-core_2.12/latest/doobie/util/fragments$.html

trait UsersDAOEdit {
  def edit(
      oldUsername: String,
      newUsername: Option[String],
      password: Option[String],
      emailAddress: Option[String],
      notes: Option[String]
  ) = {
    // val usernameFr: Fragment = newUsername.map(s => fr"username == $s")
    // sql"UPDATE users_with_meta ${setOpt(usernameFr)} WHERE username = '$oldUsername'".update
    sql"UPDATE users_with_meta SET password = 'edited-password' WHERE username = 'demo_admin'".update.run
  }
}
