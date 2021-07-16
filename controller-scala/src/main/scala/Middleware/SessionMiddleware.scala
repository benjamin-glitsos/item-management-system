import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object SessionMiddleware extends StringMixin {
  final def apply(): Directive0 =
    extractRequest flatMap { request =>
      {
        println(request.method.name)
        println(request.uri.toString)
        println(request.headers.map(_.toString))
        println("=============================")
        if (true) {
          pass
        } else {
          reject(new AuthorisationFailedRejection)
        }
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

// import akka.http.scaladsl.server._
// import akka.http.scaladsl.server.Directives._
// import scala.concurrent.duration._
// import akka.http.scaladsl.model.HttpEntity
// import cats.implicits._
// import cats.data.Validated.{Valid, Invalid}
//
// object SessionMiddleware extends StringMixin {
//   final def apply(): Directive0 =
//     extractStrictEntity(3.seconds)
//       .flatMap((entity: HttpEntity.Strict) => {
//         if (staticEndpoints contains endpointName) {
//           provide(ujsonEmptyValue)
//         } else {
//           val entityText = entity.data.utf8String
//
//           SchemaValidation(endpointName, entityText) match {
//             case Valid(v) => provide(v)
//             case Invalid(e) =>
//               reject(
//                 ValidationRejection(serialiseErrors(e))
//               )
//           }
//         }
//       })
// }