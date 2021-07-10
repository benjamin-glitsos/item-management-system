import doobie_import.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait UsersAuthenticateDAO {
  final def authenticate(
      username: String,
      password: String
  ) = fr"SELECT authenticate($username, $password)".query[Boolean].unique
}
