import java.util.UUID
import java.util.UUID.randomUUID
import redis.clients.jedis.Jedis

trait SessionMixin extends TupleMixin {
  val redis: Jedis = {
    val r = new Jedis("session-redis")
    r.auth(System.getenv("SESSION_PASSWORD"))
    r
  }

  final def makeAuthenticationToken(
      metakey: String,
      sessionToken: UUID
  ): String = s"$metakey.$sessionToken"

  final def splitAuthenticationToken(
      authenticationToken: String
  ): (String, UUID) = {
    val t = bifurcate(authenticationToken)
    t.copy(_2 = UUID.fromString(t._2))
  }

  final def randomSessionToken(): UUID = randomUUID()

  final def sessionNamespace(key: String): String = s"session:$key"
}
