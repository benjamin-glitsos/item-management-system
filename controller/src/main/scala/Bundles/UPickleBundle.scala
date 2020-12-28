import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.{ContentTypeRange, HttpEntity}
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import cats.data.NonEmptyChain

package upickle_bundle {
  object implicits {
    implicit val upickleLocalDateTime: ReadWriter[LocalDateTime] =
      readwriter[ujson.Value].bimap[LocalDateTime](
        x => x.toString(),
        json => {
          val defaultFormat: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
          LocalDateTime.parse(json.toString(), defaultFormat)
        }
      )

    implicit val upickleUsersWithMeta: ReadWriter[UsersWithMeta] = macroRW

    implicit val upickleError: ReadWriter[Error] = macroRW

    implicit def upickleMarshaller: ToEntityMarshaller[ujson.Value] = {
      Marshaller.withFixedContentType(`application/json`) { a =>
        HttpEntity(`application/json`, ujson.write(a))
      }
    }

    implicit def errorMarshaller: ToEntityMarshaller[Error] = {
      Marshaller.withFixedContentType(`application/json`) { a =>
        HttpEntity(`application/json`, ujson.write(a))
      }
    }

    val ujsonEmptyValue: ujson.Value = ujson.write(ujson.Obj())
  }
}
