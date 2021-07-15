import redis.clients.jedis.Jedis

trait SessionMixin extends SeederMixin {
  final val redis = {
    val r = new Jedis("localhost", System.getenv("REDIS_PORT").toInt)
    // r.auth(System.getenv("REDIS_PASSWORD"))
    r
  }

  final def randomSessionToken(): String = randomAlphanumerics(32)

  final def makeAuthenticationToken(
      metakey: String,
      sessionToken: String
  ): String = s"$metakey.$sessionToken"
}
