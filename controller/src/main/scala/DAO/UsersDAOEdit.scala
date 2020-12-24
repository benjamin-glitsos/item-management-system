import doobie_bundle.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
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
    // val setOptUsername: Option[Fragment] =
    //   newUsername.map(s => fr"username = $s")
    // sql"UPDATE users_with_meta ${setOpt(usernameFr)} WHERE username = '$oldUsername'".update
    val updateFr: Fragment =
      fr"UPDATE users_with_meta SET password = $password"
    val whereFr: Fragment = whereAnd(fr"username = $oldUsername")
    // sql"UPDATE users_with_meta SET password = 'edited-password-2' WHERE username = $oldUsername".update.run
    val q = updateFr ++ whereFr
    q.update.run
  }
}
