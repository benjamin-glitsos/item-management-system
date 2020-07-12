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

    val db = Database.forConfig("database")

    val users = TableQuery[Users]

    val queries = DBIO.seq(
      users.schema.create,
      users += (1, "aaaa"),
      users += (2, "bbbb")
    )

    def main(args: Array[String]) = {
        val setup = db.run(queries)
        println("Hello World")
    }
}
