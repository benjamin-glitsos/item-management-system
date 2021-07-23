import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain

trait RejectionMixin extends ErrorMixin with ErrorMessagesMixin {
  final def internalServerErrorRejection(): StandardRoute = complete(
    InternalServerError,
    formatErrorsNec(NonEmptyChain(internalServerError()))
  )

  final def notFoundRejection(): StandardRoute = complete(
    NotFound,
    formatErrorsNec(NonEmptyChain(notFoundError()))
  )

  final def badRequestRejection(body: ujson.Value): StandardRoute = complete(
    BadRequest,
    body
  )
}
