import java.sql.Timestamp
import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.{ Decoder, Encoder, HCursor, Json }
import io.circe.generic.auto._
import scala.concurrent._

object UsersRoutes {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    implicit val TimestampFormat : Encoder[Timestamp] with Decoder[Timestamp] = new Encoder[Timestamp] with Decoder[Timestamp] {
        override def apply(a: Timestamp): Json = Encoder.encodeLong.apply(a.getTime)
        override def apply(c: HCursor): Decoder.Result[Timestamp] = Decoder.decodeLong.map(s => new Timestamp(s)).apply(c)
    }

    object Id extends QueryParamDecoderMatcher[Int]("id")
    object MaybeId extends OptionalQueryParamDecoderMatcher[Int]("id")
    object MaybeRows extends OptionalQueryParamDecoderMatcher[Int]("rows")
    object MaybePage extends OptionalQueryParamDecoderMatcher[Int]("page")

    val service = HttpRoutes.of[IO] {
        case GET -> Root :? MaybeId(maybeId) +& MaybeRows(maybeRows) +& MaybePage(maybePage) => {
            val rows = maybeRows.getOrElse(25) // TODO: move these into the Service. The parameters will take maybeRows, maybePage
            val page = maybePage.getOrElse(1)
            maybeId match {
                case None =>
                    Ok(IO.fromFuture(IO(UsersService.list(rows, page))))
                // case Some(id) =>
                //     IO.fromFuture(IO(UsersService.show(id))).flatMap(_.fold(NotFound())(Ok(_)))
            }
        }
        // case DELETE -> Root :? Id(id) =>
        //     Ok(IO.fromFuture(IO(UsersService.delete(id))))
    }
}
