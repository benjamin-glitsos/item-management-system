import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.HttpHeader

trait HttpHeadersMixin {
  final def accessControlAllowOriginHeader(allowedOrigin: String): HttpHeader =
    RawHeader("Access-Control-Allow-Origin", allowedOrigin)

  final def accessControlAllowMethodsHeader(
      allowedMethods: List[String]
  ): HttpHeader =
    RawHeader(
      "Access-Control-Allow-Methods",
      allowedMethods.mkString(", ")
    )

  final def accessControlAllowHeadersHeader(
      allowedHeaders: String
  ): HttpHeader =
    RawHeader("Access-Control-Allow-Headers", allowedHeaders)

  final def actionKeyHeader(actionKey: String): HttpHeader =
    RawHeader("X-Action-Key", actionKey)
}
