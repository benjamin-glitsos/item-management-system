import redis.clients.jedis.Jedis

trait SessionMixin extends SeederMixin {
  // final val redis = new Jedis("localhost", System.getenv("REDIS_PORT").toInt)
  //   val r = new Jedis("localhost", System.getenv("REDIS_PORT").toInt)
  //   r.auth(System.getenv("REDIS_PASSWORD"))
  //   r
  // }
  val redis = new Jedis("session-redis")
  redis.auth(System.getenv("REDIS_PASSWORD"))
  redis.set("foo", "bar")
  val r = redis.get("foo")
  println(r)

  final def randomSessionToken(): String = randomAlphanumerics(32)

  final def makeAuthenticationToken(
      metakey: String,
      sessionToken: String
  ): String = s"$metakey.$sessionToken"
}
