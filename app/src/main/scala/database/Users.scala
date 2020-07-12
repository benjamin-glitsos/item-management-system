import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UsersTable(tag: Tag) extends Table[(Int, String)](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name)
}

object Users extends Seeder {
    val users = TableQuery[UsersTable]

    def dropCreate() = {
        DBIO.seq(
          users.schema.drop,
          users.schema.create
        )
    }

    def seed() = {
        DBIO.seq(
            users ++= Seq(
                (0, newPerson().getFirstName()),
                (0, newPerson().getFirstName())
            )
        )
    }
}
