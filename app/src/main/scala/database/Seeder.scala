import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

trait Seeder {
    def newPerson(): Person = {
        Fairy.create().person()
    }

    def seed[A](min: Int, max: Int, row: => A) = {
        val length = Random.between(min, max)
        (1 to length).map(_ => row)
    }
}
