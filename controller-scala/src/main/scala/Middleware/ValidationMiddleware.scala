import scala.util.{Success, Failure}
import cats.data.Validated.{Valid, Invalid}
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import akka.http.scaladsl.model.HttpEntity
import cats.implicits._

object ValidationMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin {
  private final val whitelist =
    List("open-users", "open-items", "logout-sessions")

  final def apply(): Directive1[ujson.Value] =
    headerValueByName("X-Action-Key") flatMap { actionKey: String =>
      extractStrictEntity(1.seconds) flatMap { entity: HttpEntity.Strict =>
        if (whitelist contains actionKey) {
          provide(ujsonEmptyValue)
        } else {
          val body: String = entity.data.utf8String

          Await.result(SchemaValidation(actionKey, body), 1.seconds) match {
            case Valid(v)   => provide(v)
            case Invalid(e) => badRequestRejection(necToJson(e))
          }

        }
      }
    }
}
