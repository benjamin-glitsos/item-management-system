import org.http4s.implicits._
import org.http4s.server.Router
import bundles.doobie.connection._

import cats.effect._
import cats.implicits._
import org.http4s._, org.http4s.dsl.io._, org.http4s.implicits._

import java.time.LocalDateTime
import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.Json
import org.http4s.circe._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import io.circe.optics.JsonPath._
import doobie.implicits._
import bundles.doobie.connection._
import bundles.http4s._
import cats.data.Validated.{Invalid, Valid}

import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

object Routes {
    val router = Router(
        "users" -> UsersRoutes.endpoints
    ).orNotFound
}
