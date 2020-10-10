import cats.data.Kleisli
import cats.effect._
import cats.implicits._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._

def JsonReqMiddle(service: HttpRoutes[IO], header: Header): HttpRoutes[IO] = Kleisli { req: Request[IO] =>
  service(req).map {
    case Status.Successful(resp) =>
      resp.putHeaders(header)
    case resp =>
      resp
  }
}
