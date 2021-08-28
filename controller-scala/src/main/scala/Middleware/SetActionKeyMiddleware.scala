import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpRequest

object SetActionKeyMiddleware extends HttpHeadersMixin {
  final def apply(actionKey: String): Directive0 =
    mapRequest((req: HttpRequest) =>
      req.mapHeaders(_ ++ Seq(actionKeyHeader(actionKey)))
    )
}
