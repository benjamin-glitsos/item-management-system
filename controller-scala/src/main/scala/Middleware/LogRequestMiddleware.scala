import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpRequest
import java.time.LocalDateTime
import org.apache.logging.log4j.{LogManager, Logger};

object LogRequestMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin {
  private final val log: Logger = LogManager.getLogger("request-logger")

  final def apply(): Directive0 =
    mapRequest((req: HttpRequest) => {
      log.info(
        s"${req.method.name} ${req.uri} ${req.protocol.value} ${req.getHeader("X-Action-Key")}"
      )
      req
    })
}
