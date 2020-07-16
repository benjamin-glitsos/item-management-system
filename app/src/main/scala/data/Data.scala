import java.sql.Timestamp
import slick.driver.PostgresDriver.api._
import SchemaTypes._

object Data extends Seeder {
    val schema = RecordsDAO.schema ++ UsersDAO.schema ++ PeopleDAO.schema

    def setup() = {
        DBIO.seq(
            schema.create,
            RecordsDAO ++= seed[Record](
                RecordsDAO.seedCount,
                (
                    id,
                    new Timestamp(System.currentTimeMillis()),
                    randFK(UsersDAO.seedCount),
                    Some(new Timestamp(System.currentTimeMillis())),
                    Some(randFK(UsersDAO.seedCount)),
                    Some(new Timestamp(System.currentTimeMillis())),
                    Some(randFK(UsersDAO.seedCount))
                )
            ),
            UsersDAO ++= seed[User](
                UsersDAO.seedCount,
                (
                    id,
                    randFK(RecordsDAO.seedCount),
                    newPerson().getUsername(),
                    newPerson().getPassword()
                )
            ),
        )
    }
}
