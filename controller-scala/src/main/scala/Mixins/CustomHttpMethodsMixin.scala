import akka.http.scaladsl.model.HttpMethod
import akka.http.scaladsl.model.RequestEntityAcceptance.Expected
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

trait CustomHttpMethodsMixin {
  final val REPORT: HttpMethod = HttpMethod.custom(
    "REPORT",
    safe = true,
    idempotent = true,
    requestEntityAcceptance = Expected
  )

  final val report: Directive0 = method(REPORT)

  final val allCustomHttpMethods: List[HttpMethod] = List(REPORT)
}
