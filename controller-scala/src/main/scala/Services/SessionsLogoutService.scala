trait SessionsLogoutService extends SessionMixin {
  final def logout(authenticationToken: String): String = {
    try {
      redis.del(sessionNamespace(authenticationToken))
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
