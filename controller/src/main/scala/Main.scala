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

    println("HEY")
    // case class Users(id: Int, person_id: Int, username: String, password: String)
    // val users = quote {
    //     query[Users]
    // }
    // println(ctx.run(users));

    val myAppLogic =
        for {
            _ <- putStrLn("Hello!")
        } yield ()
}
