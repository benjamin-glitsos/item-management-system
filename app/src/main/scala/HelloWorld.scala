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
    val fairy: Fairy = Fairy.create()

    val db = Database.forURL(
        s"jdbc:postgresql://${System.getenv("POSTGRES_HOST")}/${System.getenv("POSTGRES_DB")}",
        driver = "org.postgresql.Driver",
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD")
    )

    val users = TableQuery[Users]

    // object UsersDAO extends TableQuery(new Users(_)) {
    //     def findById(id: Int): Future[Option[Users]] = {
    //         db.run(this.filter(_.id === id).result).map(_.headOption)
    //     }
    // }

    val queries = DBIO.seq(
      users.schema.create,
      users ++= Seq(
          (1, fairy.person().getFirstName()),
          (2, fairy.person().getFirstName())
      ),
    )

    def main(args: Array[String]) = {
        val setup = db.run(queries)
        println(
            db.run(users.result).map(_.foreach {
              case (id, name) =>
                id + name
            }).mkString("\n")
        )
    }
}
