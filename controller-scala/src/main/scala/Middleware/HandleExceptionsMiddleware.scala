import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpResponse

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import StatusCodes._
import Directives._

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

// TODO: try using Route.seal with this and rejections as implicits rather than using two middlewares
// TODO: if not, use concat to combine the middleware in the version1 route
