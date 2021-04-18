import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object CustomMethodMiddlewares {
  final val report: Directive0 = method(CustomMethods.REPORT)
}
