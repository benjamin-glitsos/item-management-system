import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object HandleRejections {
  final def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handle {
        case ValidationRejection(se: String, _) =>
          RejectionRoutes.internalServerError(se)
        case AuthorizationFailedRejection =>
          RejectionRoutes.authorisationFailed()
      }
      .handleNotFound {
        RejectionRoutes.notFound()
      }
      .result()
  )
}
