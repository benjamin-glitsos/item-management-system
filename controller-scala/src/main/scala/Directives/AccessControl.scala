import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.headers.RawHeader

object AccessControl {
  def apply(): Directive0 = {
    val allowOriginUrl = if (System.getenv("PROJECT_MODE") === "production") {
      val adminDomain = System.getenv("PROJECT_DOMAIN")
      s"https://$adminDomain"
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
