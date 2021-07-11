import redis.clients.jedis.Jedis

trait RedisMixin {
  final val redis = {
    val r = new Jedis("localhost", System.getenv("REDIS_PORT").toInt)
    r.auth(System.getenv("REDIS_PASSWORD"))
    r
  }
}
