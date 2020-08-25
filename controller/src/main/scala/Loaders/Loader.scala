import java.sql.Timestamp
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

object Loader {
    def multiply[A](count: Int, x: => A): Iterable[A] = {
        (1 to count).map(_ => x)
    }

}
