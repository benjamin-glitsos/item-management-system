import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.concurrent.duration._
import akka.http.scaladsl.server.Directive0

object ApiRoutes {
  def apply(): Route =
    cachingProhibited {
      RequiredContentTypeDirective {
        extractStrictEntity(3.seconds) { entity =>
          concat(
              pathPrefix("v1")(Version1Routes(entity.data.utf8String))
          )
        }
      }
    }
}
