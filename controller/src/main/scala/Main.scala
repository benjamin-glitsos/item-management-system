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
// import ctx._

object MyApp extends zio.App {

    // lazy val ctx = new PostgresJdbcContext(SnakeCase, "ctx")

    def run(args: List[String]) =
        myAppLogic.exitCode

    val myAppLogic =
        for {
            _    <- putStrLn("Hello! What is your name?")
            name <- getStrLn
            _    <- putStrLn(s"Hello, ${name}, welcome to ZIO!")
        } yield ()
}
