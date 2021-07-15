import akka.http.scaladsl.model.HttpMethod
import akka.http.scaladsl.model.RequestEntityAcceptance.Expected

trait CustomHttpMethodsMixin {
  final val REPORT: HttpMethod = HttpMethod.custom(
    "REPORT",
    safe = true,
    idempotent = true,
    requestEntityAcceptance = Expected
  )
}
