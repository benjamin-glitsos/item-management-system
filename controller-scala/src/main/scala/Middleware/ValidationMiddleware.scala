import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import scala.concurrent.duration._
import akka.http.scaladsl.model.HttpEntity
import cats.implicits._
import cats.data.Validated.{Valid, Invalid}

object ValidationMiddleware extends ErrorMixin with UpickleMixin {
  private final val staticEndpoints = List("open-users", "open-items")

  final def apply(endpointName: String): Directive1[ujson.Value] =
    extractStrictEntity(3.seconds) flatMap { (entity: HttpEntity.Strict) =>
      if (staticEndpoints contains endpointName) {
        provide(ujsonEmptyValue)
      } else {
        val entityText = entity.data.utf8String

        SchemaValidation(endpointName, entityText) match {
          case Valid(v) => provide(v)
          case Invalid(e) =>
            complete(
              BadRequest,
              formatErrors(e)
            )
        }
      }
    }
}
