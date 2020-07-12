// import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

trait Seeder {
    // def newFaker(): Fairy = {
    //     Fairy.create()
    // }

    def newPerson(): Person = {
        Fairy.create().person()
    }
}
