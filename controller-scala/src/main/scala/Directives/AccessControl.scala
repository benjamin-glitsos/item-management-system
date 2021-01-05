import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.headers.RawHeader

object AccessControl {
  def apply(): Directive0 = respondWithDefaultHeaders(
    Seq(
      RawHeader("Access-Control-Allow-Origin", "*"),
      RawHeader(
        "Access-Control-Allow-Methods",
        "OPTIONS, GET, POST, DELETE, PATCH, REPORT"
      ),
      RawHeader("Access-Control-Allow-Headers", "*")
    )
  )
}
