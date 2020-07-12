import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

object HelloWorld {
    val person: Person = Fairy.create().person()
    def main(args: Array[String]) = {
        println(person)
    }
}
