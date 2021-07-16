import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object HandleRejectionsMiddleware {
  final def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handle {
        case ValidationRejection(se: String, _) =>
          RejectionRoutes.badRequestError(se)
        case MissingHeaderRejection(headerName: String) =>
          RejectionRoutes.missingHeader(headerName)
      }
      .handleNotFound {
        RejectionRoutes.notFound()
      }
      .result()
  )
}
