import redis.clients.jedis.Jedis

trait SessionMixin extends SeederMixin {
  val redis: Jedis = {
    val r = new Jedis("session-redis")
    r.auth(System.getenv("REDIS_PASSWORD"))
    r
  }

  final def randomSessionToken(): String = randomAlphanumerics(32)

  final def makeAuthenticationToken(
      metakey: String,
      sessionToken: String
  ): String = s"$metakey.$sessionToken"

  final def decomposeAuthenticationToken(
      authenticationToken: String
  ): List[String] = s.split(separator).toList

  final def sessionNamespace(key: String): String = s"session:$key"
}
