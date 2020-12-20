import akka.http.scaladsl.server._

object RequiredContentTypeDirective {
  // TODO: make this take a ContentTypes value then convert it to a string
  def apply(): Directive0 =
    extract(_.request.headers.exists {
      case x: `Content-Type` =>
        x.directives.exists {
          case `application/json` => true
          case _                  => false
        }
      case _ => false
    }).flatMap(if (_) pass else reject)
}
