import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object CorsMiddleware extends HttpHeadersMixin {
  def apply(): Directive0 = {
    val allowedOrigin = {
      if (System.getenv("PROJECT_MODE") == "production") {
        val projectDomain  = System.getenv("PROJECT_DOMAIN")
        val adminSubdomain = System.getenv("ADMIN_SUBDOMAIN")
        s"https://$adminSubdomain.$projectDomain"
      } else {
        val adminPort = System.getenv("ADMIN_PORT")
        s"http://localhost:$adminPort"
      }
    }

    respondWithDefaultHeaders(
      Seq(
        accessControlAllowOriginHeader(allowedOrigin),
        accessControlAllowMethodsHeader(
          List("OPTIONS", "GET", "POST", "DELETE", "PATCH", "REPORT")
        ),
        accessControlAllowHeadersHeader("*")
      )
    )
  }
}
