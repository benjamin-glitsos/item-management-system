import slick.driver.PostgresDriver.api._
import slick.jdbc.meta.MTable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Users extends Seeder {
    type User = (Int, String, String)

    class UsersTable(tag: Tag) extends Table[User](tag, "users") {
        def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

        def username = column[String]("username")

        def password = column[String]("password")

        def * = (id, username, password)
    }

    val users = TableQuery[UsersTable]

    def initialise() = DBIO.seq(
        users.schema.drop,
        users.schema.create,
        users ++= seed[User](
            1,
            10,
            (0, newPerson().getUsername(), newPerson().getPassword())
            )
        )
}
