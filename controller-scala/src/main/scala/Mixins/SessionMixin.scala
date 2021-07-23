import redis.clients.jedis.Jedis

trait SessionMixin extends SeederMixin with TupleMixin {
  val redis: Jedis = {
    val r = new Jedis("session-redis")
    r.auth(System.getenv("SESSION_PASSWORD"))
    r
  }

  final def randomSessionToken(): String = randomAlphanumerics(32)

  final def makeAuthenticationToken(
      metakey: String,
      sessionToken: String
  ): String = s"$metakey.$sessionToken"

  final def splitAuthenticationToken(
      authenticationToken: String
  ): (String, String) = bifurcate(authenticationToken)

  final def sessionNamespace(key: String): String = s"session:$key"
}
