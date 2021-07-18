import doobie.implicits._

trait UsersAuthenticateDAO extends DoobieDatabaseMixin {
  final def authenticate(
      username: String,
      password: String
  ) = fr"SELECT authenticate_for_users_open($username, $password)"
    .query[String]
    .option
}
