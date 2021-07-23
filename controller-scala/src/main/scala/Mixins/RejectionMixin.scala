import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain

trait RejectionMixin extends ErrorMixin with ErrorMessagesMixin {
  final def internalServerErrorRejection(): StandardRoute = complete(
    InternalServerError,
    wrapErrors(
      necToJson(NonEmptyChain(internalServerError()))
    )
  )

  final def notFoundRejection(): StandardRoute = complete(
    NotFound,
    wrapErrors(
      necToJson(NonEmptyChain(notFoundError()))
    )
  )

  final def badRequestRejection(body: ujson.Value): StandardRoute = complete(
    BadRequest,
    wrapErrors(body)
  )
}
