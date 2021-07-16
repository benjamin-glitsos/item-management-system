import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.headers.RawHeader

object CorsMiddleware {
  def apply(): Directive0 = {
    val allowOriginUrl = if (System.getenv("PROJECT_MODE") == "production") {
      val projectDomain  = System.getenv("PROJECT_DOMAIN")
      val adminSubdomain = System.getenv("ADMIN_SUBDOMAIN")
      s"https://$adminSubdomain.$projectDomain"
    } else {
      val adminPort = System.getenv("ADMIN_PORT")
      s"http://localhost:$adminPort"
    }
    respondWithDefaultHeaders(
      Seq(
        RawHeader(
          "Access-Control-Allow-Origin",
          allowOriginUrl
        ),
        RawHeader(
          "Access-Control-Allow-Methods",
          "OPTIONS, GET, POST, DELETE, PATCH, REPORT"
        ),
        RawHeader("Access-Control-Allow-Headers", "*")
      )
    )
  }
}
