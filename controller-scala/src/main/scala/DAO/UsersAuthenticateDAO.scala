import doobie.implicits._

trait UsersAuthenticateDAO extends DoobieDatabaseTrait {
  final def authenticate(
      username: String,
      password: String
  ) = fr"SELECT authenticate($username, $password)".query[Boolean].unique
}
