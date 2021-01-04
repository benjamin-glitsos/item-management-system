import akka.http.scaladsl.model.HttpMethod
import akka.http.scaladsl.model.RequestEntityAcceptance.Expected

object CustomMethods {
  final val REPORT = HttpMethod.custom(
    "REPORT",
    safe = true,
    idempotent = true,
    requestEntityAcceptance = Expected
  )

  final def apply() = List[REPORT]
}
