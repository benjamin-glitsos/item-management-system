import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UsersTable(tag: Tag) extends Table[(Int, String)](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name)
}

object Users extends TableQuery(new UsersTable(_)) with Seeder {
    def dropCreate() = {
        DBIO.seq(
          this.schema.drop,
          this.schema.create
        )
    }

    def seed() = {
        DBIO.seq(
            this ++= Seq(
                (0, newPerson().getFirstName()),
                (0, newPerson().getFirstName())
            )
        )
    }
}
