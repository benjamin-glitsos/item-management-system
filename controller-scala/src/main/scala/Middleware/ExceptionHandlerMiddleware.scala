import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import java.time.LocalDateTime
import org.apache.logging.log4j.{LogManager, Logger};

object ExceptionHandlerMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin {
  private final val log: Logger =
    LogManager.getLogger(this.getClass.getSimpleName)

  final def apply(): Directive0 = extractRequest flatMap { request =>
    handleExceptions(
      ExceptionHandler {
        case e: Exception => {
          log.error(e)
          println(e.cause)
          internalServerErrorRejection()
        }
      }
    )
  }
}
