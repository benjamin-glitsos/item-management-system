import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpRequest, HttpHeader}

object SetActionKeyMiddleware {
  final def apply(actionKey: String): Directive0 =
    mapRequest((req: HttpRequest) => {
      val actionKeyHeader = RawHeader("X-Action-Key", actionKey)
      req.mapHeaders(_ ++ Seq(actionKeyHeader))
    })
}
