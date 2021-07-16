import java.sql.SQLException

trait SessionsLogoutService extends ServiceMixin with SessionMixin {
  final def logout(authenticationToken: String): String = {
    try {
      val (metakey, sessionToken) = decomposeAuthenticationToken(
        authenticationToken
      )
      redis.del(sessionNamespace(metakey))
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
