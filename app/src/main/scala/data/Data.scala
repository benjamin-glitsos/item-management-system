import java.sql.Timestamp
import slick.driver.PostgresDriver.api._
import SchemaTypes._

object Data extends Seeder {
    val schema = RecordsDAO.schema ++ PeopleDAO.schema ++ UsersDAO.schema

    def setup() = {
        DBIO.seq(
            sqlu"SET FOREIGN_KEY_CHECKS = 0",
            sqlu"DROP TABLE IF EXISTS #${RecordsDAO.baseTableRow.tableName}",
            sqlu"DROP TABLE IF EXISTS #${PeopleDAO.baseTableRow.tableName}",
            sqlu"DROP TABLE IF EXISTS #${UsersDAO.baseTableRow.tableName}",
            sqlu"SET FOREIGN_KEY_CHECKS = 1",
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
            PeopleDAO ++= seed[Person](
                PeopleDAO.seedCount,
                (
                    id,
                    randFK(RecordsDAO.seedCount),
                    newPerson().getFirstName(),
                    newPerson().getLastName(),
                    "",
                    newPerson().getMobileNumber(),
                )
            ),
            UsersDAO ++= seed[User](
                UsersDAO.seedCount,
                (
                    id,
                    randFK(PeopleDAO.seedCount),
                    newPerson().getUsername(),
                    newPerson().getPassword()
                )
            ),
        )
    }
}
