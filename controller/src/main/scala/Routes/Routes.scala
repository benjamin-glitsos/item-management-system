import org.http4s.implicits._
import org.http4s.server.Router
import bundles.doobie.connection._

object Routes {
    val router = Router(
        "users" -> UsersRoutes.endpoints
    ).orNotFound
}
