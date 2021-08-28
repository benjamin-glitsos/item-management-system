import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpEntity
import scala.concurrent.duration._
import org.apache.logging.log4j.{LogManager, Logger};

object LogRequestMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin {
  private final val log: Logger = LogManager.getLogger("request-logger")

  final def apply(): Directive0 =
    extractStrictEntity(1.seconds) flatMap { entity: HttpEntity.Strict =>
      mapRequest((req: HttpRequest) => {
        log.info(
          List(
            req.hashCode,
            req.method.name,
            req.uri,
            req.encoding.value,
            entity.data.utf8String
          ).mkString(" ")
        )
        req
      })
    }
}
