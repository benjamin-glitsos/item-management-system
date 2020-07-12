import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object HelloWorld {
    val person: Person = Fairy.create().person()
    val db = Database.forConfig("database")

    class Users(tag: Tag) extends Table[(Int, String)](tag, "USERS") {
        def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
        def name = column[String]("NAME")
        def * = (id, name)
    }

    object UsersDAO extends TableQuery(new Users(_)) {
        // def findById(id: Int): Future[Option[Users]] = {
        //     db.run(this.filter(_.id === id).result).map(_.headOption)
        // }
    }

    def main(args: Array[String]) = {
        println("Hello World")
        // UsersDAO.findById(1)
        // val schema = UsersDAO.schema
        // schema.create.statements
    }
}
