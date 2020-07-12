import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Users(tag: Tag) extends Table[(Int, String)](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name)
}

object HelloWorld {
    val faker: Fairy = Fairy.create()

    val db = Database.forURL(
        s"jdbc:postgresql://${System.getenv("POSTGRES_HOST")}/${System.getenv("POSTGRES_DB")}",
        driver = "org.postgresql.Driver",
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD")
    )

    object UsersDAO extends TableQuery(new Users(_)) {
        def findById(id: Int) = {
            db.run(this.filter(_.id === id).result)
        }
    }

    val queries = DBIO.seq(
      UsersDAO.schema.drop,
      UsersDAO.schema.create,
      UsersDAO ++= Seq(
          (0, faker.person().getFirstName()),
          (0, faker.person().getFirstName())
      ),
    )

    def main(args: Array[String]) = {
        val setup = db.run(queries)
        // val select = db.run(users.result).foreach(println)
    }
}
