import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object HelloWorld {
    val db = Database.forConfig("database")
    val person: Person = Fairy.create().person()
    def main(args: Array[String]) = {
        System.getenv.forEach((name, value) => println(s"$name: $value"))
    }
}
