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
    val person: Person = Fairy.create().person()

    // TODO: put this in a config file e.g Typesafe Config
    val db = Database.forURL(
        s"jdbc:postgresql://${System.getenv("POSTGRES_HOST")}/${System.getenv("POSTGRES_DB")}",
        driver = "org.postgresql.Driver",
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD")
    )

    val users = TableQuery[Users]

    val queries = DBIO.seq(
      users.schema.create,
      users += (1, person.getFirstName()),
      users += (2, person.getFirstName()),
    )

    def main(args: Array[String]) = {
        val setup = db.run(queries)
        println("Hello World")
    }
}
