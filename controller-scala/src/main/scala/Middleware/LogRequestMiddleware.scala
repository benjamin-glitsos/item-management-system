import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpEntity
import scala.concurrent.duration._
import org.apache.logging.log4j.{LogManager, Logger};
import upickle.default._

object LogRequestMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin
    with StringMixin {
  private final val log: Logger = LogManager.getLogger("request-logger")

  final def apply(): Directive0 =
    extractStrictEntity(1.seconds) flatMap { entity: HttpEntity.Strict =>
      mapRequest((req: HttpRequest) => {
        val body: String = write(read[ujson.Value](entity.data.utf8String))
        log.info(
          List(
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
