import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Users(tag: Tag) extends Table[(Int, String)](tag, "USERS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def * = (id, name)
}

object HelloWorld {
    val person: Person = Fairy.create().person()

    val db = Database.forURL(
        s"jdbc:postgresql://${System.getenv("POSTGRES_HOST")}/${System.getenv("POSTGRES_DB")}",
        driver = "org.postgresql.Driver",
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD")
    )

    val users = TableQuery[Users]

    val queries = DBIO.seq(
      users.schema.create,
      users += (1, "aaaa"),
      users += (2, "bbbb"),
      users += (2, "cccc"),
      users += (2, "dddd")
    )

    def main(args: Array[String]) = {
        val setup = db.run(queries)
        println("Hello World")
    }
}
