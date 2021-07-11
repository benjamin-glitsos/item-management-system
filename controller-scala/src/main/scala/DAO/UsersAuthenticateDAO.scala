import doobie.implicits._

trait UsersAuthenticateDAO extends DoobieDatabaseMixin {
  final def authenticate(
      username: String,
      password: String
  ) = fr"SELECT authenticate($username, $password)".query[String].option
}
