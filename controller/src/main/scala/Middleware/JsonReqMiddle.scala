import cats.data.Kleisli
import cats.effect._
import cats.implicits._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._

object ReqContractValidateMiddle {
    def validateByContract(req: Request[IO]): HttpRoutes[IO] = {
        openApiValidation(req) match {
            case Valid(req) => req
            case Invalid(err) => BadRequest(err)
        }
    }

    def apply(service: HttpRoutes[IO]): HttpRoutes[IO] = {
        service.map(validateByContract(_))
    }
}
