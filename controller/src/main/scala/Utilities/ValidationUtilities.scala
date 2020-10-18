import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel
import scala.util.matching.Regex
import cats.effect.IO
import org.http4s.Response
import io.circe.Json
import cats.data.Validated.{Invalid, Valid}
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._

import java.io.IOException
import java.sql.SQLException

trait ValidationUtilities {
    type Validation[A] = ValidatedNel[Error, A]

    def multipleValidations[A](value: A, validators: List[A => Validation[A]]) = {
        validators
            .map(f => f(value))
            .fold(value.validNel)((a: Validation[A], b: Validation[A]) => a *> b)
    }

    def doesStringContainPattern(string: String, pattern: Regex, error: Error): Validation[String] = {
        pattern.findFirstMatchIn(string) match {
            case Some(_) => string.validNel
            case None => error.invalidNel
        }
    }

    def handleResponse(res: Validation[Json]): IO[Response[IO]] = {
        try {
            res match {
                case Valid(res) => Ok(res)
                case Invalid(err) => BadRequest(err)
            }
        } catch {
            case err: SQLException => BadRequest(Errors.databaseException(err.getMessage))
            case err: IOException => BadRequest(Errors.ioException(err.getMessage))
            case err: Exception => BadRequest(Errors.generalException(err.getMessage))
            case _: Throwable => BadRequest(Errors.unexpectedError())
        }
    }
}
