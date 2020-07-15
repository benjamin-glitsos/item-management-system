import java.sql.Timestamp
import slick.driver.PostgresDriver.api._
import Types._

object Data extends Seeder {
    def setup(): Unit = {
        DBIO.seq(
            UsersDAO.schema.create,
            UsersDAO ++= generate[User](
                UsersDAO.seedCount,
                (
                    id,
                    randFK(RecordsDAO.seedCount),
                    newPerson().getUsername(),
                    newPerson().getPassword()
                )
            ),
            RecordsDAO.schema.create,
            RecordsDAO ++= generate[Record](
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
        )
    }
}
