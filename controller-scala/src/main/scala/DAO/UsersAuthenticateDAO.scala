import doobie_import.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait UsersAuthenticateDAO {
  final def authenticate(
      username: String,
      password: String
  ) = {
    fr"""
    SELECT true
    """.query[Boolean].unique
  }
}
// SELECT EXISTS (
//   SELECT true FROM users
//   WHERE username = $username
//   AND   password = sha1_encrypt($password)
// )
// FROM users
