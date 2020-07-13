import scala.util.Random
import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

trait Seeder {
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
