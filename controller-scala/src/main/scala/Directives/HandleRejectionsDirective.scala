import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object HandleRejectionsDirective {
  final def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handle {
        case ValidationRejection(se: String, _) =>
          RejectionRoutes.badRequestError(se)
        case AuthorizationFailedRejection =>
          RejectionRoutes.authorisationFailed()
      }
      .handleNotFound {
        RejectionRoutes.notFound()
      }
      .result()
  )
}
