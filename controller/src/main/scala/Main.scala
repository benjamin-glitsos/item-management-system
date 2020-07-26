// import cats.effect._
// import org.http4s.server.blaze._
//
// object Main extends IOApp {
//
//     def run(args: List[String]): IO[ExitCode] = {
//         Loader.setup()
//
//         BlazeServerBuilder[IO]
//             .bindHttp(
//                 System.getenv("CONTROLLER_PORT").toInt,
//                 System.getenv("DOCKER_LOCALHOST")
//             )
//             .withHttpApp(Routes.service)
//             .serve
//             .compile
//             .drain
//             .as(ExitCode.Success)
//     }
// }

import zio.console._
import io.getquill._

object Main extends zio.App {

    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    def run(args: List[String]) =
        myAppLogic.exitCode

    val pi = quote(3.14159)
    case class Circle(radius: Float)
    val areas = quote {
        query[Circle].map(c => pi * c.radius * c.radius)
    }
    ctx.run(areas)

    val myAppLogic =
        for {
            _    <- putStrLn("Hello! What is your name?")
            name <- getStrLn
            _    <- putStrLn(s"Hello, ${name}, welcome to ZIO!")
        } yield ()
}
