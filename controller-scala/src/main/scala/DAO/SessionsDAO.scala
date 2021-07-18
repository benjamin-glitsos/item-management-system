object SessionsDAO extends SessionMixin {
  def set(key: String, value: String): Unit =
    redis.set(key, value)

  def del(key: String): Unit =
    redis.del(key)

  def get(key: String): String =
    redis.get(key)
}
