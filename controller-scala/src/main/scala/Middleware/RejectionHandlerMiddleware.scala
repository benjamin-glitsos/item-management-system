import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object RejectionHandlerMiddleware extends RejectionMixin {
  final def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handleNotFound(notFoundRejection())
      .result()
  )
}
