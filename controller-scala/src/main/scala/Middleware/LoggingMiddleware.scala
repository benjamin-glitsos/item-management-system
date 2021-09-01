import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{HttpRequest, HttpEntity, RemoteAddress}
import scala.concurrent.duration._
import org.apache.logging.log4j.{LogManager, Logger};
import upickle.default._

object LoggingMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin
    with StringMixin
    with OptionMixin {
  private final val log: Logger = LogManager.getLogger("request-logger")

  final def apply(): Directive0 =
    extractStrictEntity(1.seconds) flatMap { entity: HttpEntity.Strict =>
      extractClientIP flatMap { clientIp: RemoteAddress =>
        mapRequest((req: HttpRequest) => {
          val body: String = maybeEmpty(entity.data.utf8String) match {
            case None    => write(ujson.Obj)
            case Some(s) => write(read[ujson.Value](s))
          }
          log.info(
            List(
              clientIp,
              req.method.name,
              req.uri.path.toString,
              entity.contentType.value,
              body
            ).mkString(" ")
          )
          req
        })
      }
    }
}
