import akka.http.scaladsl.server.Route

object ApiRoutes {
  def apply(): Route =
    concat(
        pathPrefix("v1")(Version1Routes())
    )
}
