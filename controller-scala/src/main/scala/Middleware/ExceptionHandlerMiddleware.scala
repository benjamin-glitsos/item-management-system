import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import org.apache.logging.log4j.{LogManager, Logger};

object ExceptionHandlerMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin {
  private final val log: Logger = LogManager.getLogger("error-logger")

  final def apply(): Directive0 = handleExceptions(
    ExceptionHandler {
      case e: Exception => {
        log.info(e)
        println(getRootCause(e))
        internalServerErrorRejection()
      }
    }
  )
}
