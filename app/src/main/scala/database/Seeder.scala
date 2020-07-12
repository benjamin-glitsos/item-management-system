import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

trait Seeder {
    def newPerson(): Person = {
        Fairy.create().person()
    }

    def seed(min: Int, max: Int) = {
        val length = Random.between(min, max)
        (1 to length).map(_ => (0, newPerson().getUsername(), newPerson().getPassword()))
    }
}
