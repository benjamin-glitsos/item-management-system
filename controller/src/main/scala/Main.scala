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

    UsersDAO.upsert(Users(2, 1, "slkfj", "slakjf"))

    // UsersServices.createOrUpdate(
    //     id = 0,
    //     user_id = 1,
    //     user = Users(0, 0, "un", "pw"),
    //     person = People(
    //         id = 0,
    //         record_id = 0,
    //         first_name = "fn",
    //         last_name = "ln",
    //         other_names = Some("on"),
    //         sex_id = 2,
    //         email_address = "test@example.com",
    //         phone_number = "0444444444",
    //         address_line_one = "43 lfkjasd st",
    //         address_line_two = "aslfj alksjdf",
    //         zip = "1111"
    //     )
    // )

    val myAppLogic =
        for {
            _ <- putStrLn("hello")
        } yield ()
}
