import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.StatusCodes._

object HandleExceptionsMiddleware {
  final def apply(): Directive0 = handleExceptions(
    ExceptionHandler { case _ =>
      extractUri { uri =>
        println(s"Exception for request to: $uri")
        complete(
          HttpResponse(
            InternalServerError,
            entity = "Error"
          )
        )
      }
    }
  )
}
