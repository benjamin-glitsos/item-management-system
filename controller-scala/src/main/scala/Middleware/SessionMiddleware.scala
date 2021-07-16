import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object SessionMiddleware extends StringMixin {
  final def apply(): Directive0[ujson.Value] =
    extractRequest { request =>
      {
        println(request.uri)
        pass(request)
      }
    }
  // headerValueByName("Authorization") { authorisationValue =>
  //   {
  //     if (isEmpty(SessionsDAO.get(authorisationToken))) {
  //       reject(AuthorisationFailedRejection())
  //     }
  //   }
  // }
}

// TODO: the Authorization header will use the "Bearer" type. So it will be like - Authorization: Bearer <authorisation_token>
