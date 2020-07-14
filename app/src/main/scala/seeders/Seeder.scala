import slick.driver.PostgresDriver.api._
import java.sql.Timestamp
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

object Seeder {
    val id: Int = 0

    def newPerson(): Person = {
        Fairy.create().person()
    }

    def randFK(max: Int): Int = {
        Random.between(1, max)
    }

    // TODO: random timestamp method

    def generate[A](count: Int, row: => A): Iterable[A] = {
        (1 to count).map(_ => row)
    }

    def run(): Unit = {
        DBIO.seq(
            RecordsDAO.schema.create,
            RecordsDAO ++= generate[Types.Record](
                RecordsDAO.seedCount,
                (
                    id,
                    new Timestamp(System.currentTimeMillis()),
                    randFK(UsersDAO.seedCount),
                    new Timestamp(System.currentTimeMillis()),
                    randFK(UsersDAO.seedCount),
                    new Timestamp(System.currentTimeMillis()),
                    randFK(UsersDAO.seedCount)
                )
            ),
            UsersDAO.schema.create,
            UsersDAO ++= generate[Types.User](
                UsersDAO.seedCount,
                (
                    id,
                    randFK(RecordsDAO.seedCount),
                    newPerson().getUsername(),
                    newPerson().getPassword()
                )
            )
        )
    }
}
