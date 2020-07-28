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

object Main extends zio.App {

    def run(args: List[String]) =
        myAppLogic.exitCode

    println(UsersDAO.list())

    val myAppLogic =
        for {
            _ <- putStrLn("hello")
        } yield ()
}
