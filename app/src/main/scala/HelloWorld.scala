import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

object HelloWorld {
    val person: Person = Fairy.create().person()
    def main(args: Array[String]) = {
        System.getenv.forEach((name, value) => println(s"$name: $value"))
    }
}
