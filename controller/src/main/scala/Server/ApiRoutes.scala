import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.concurrent.duration._

object ApiRoutes {
  def apply(): Route =
    extractStrictEntity(3.seconds) { entity =>
      concat(
          pathPrefix("v1")(Version1Routes(entity.data.utf8String))
      )
    }
}
