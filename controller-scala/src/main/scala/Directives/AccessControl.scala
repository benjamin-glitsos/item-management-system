import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.headers.RawHeader

object AccessControl {
  def apply(): Directive0 = {
    val adminPort = System.getenv("ADMIN_PORT")
    respondWithDefaultHeaders(
      Seq(
        RawHeader(
          "Access-Control-Allow-Origin",
          s"http://localhost:$adminPort"
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
