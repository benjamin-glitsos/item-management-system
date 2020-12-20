import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.HttpEntity.Chunked

object ApiRoutes {
  def apply(): Route =
    concat(
        pathPrefix("v1")(Version1Routes())
    )
}
