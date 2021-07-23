import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import akka.http.scaladsl.model.HttpEntity
import cats.implicits._
import cats.data.Validated.{Valid, Invalid}

object ValidationMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin {
  private final val whitelist =
    List("open-users", "open-items", "logout-sessions")

  final def apply(): Directive1[ujson.Value] =
    headerValueByName("X-Action-Key") flatMap { actionKey: String =>
      extractStrictEntity(3.seconds) flatMap { entity: HttpEntity.Strict =>
        if (whitelist contains actionKey) {
          provide(ujsonEmptyValue)
        } else {
          val entityText = entity.data.utf8String

          SchemaValidation(actionKey, entityText) match {
            case Valid(v)   => provide(v)
            case Invalid(e) => badRequestRejection(formatErrorsNec(e))
          }
        }
      }
    }
}
