import java.sql.Timestamp
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

trait Seeder {
    val id: Int = 0

    def newPerson(): Person = {
        Fairy.create().person()
    }

    def randFK(max: Int): Int = {
        Random.between(1, max)
    }

    // TODO: random timestamp method

    def seed[A](count: Int, row: => A): Iterable[A] = {
        (1 to count).map(_ => row)
    }
}
