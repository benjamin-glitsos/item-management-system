import java.sql.Timestamp
import slick.driver.PostgresDriver.api._
import SchemaTypes._

object Data extends Connection with Seeder {
    val schema = RecordsDAO.schema ++ PeopleDAO.schema ++ UsersDAO.schema

    def setup() = {
        DBIO.seq(
            // Reset database schema to blank state
            sqlu"DROP SCHEMA public CASCADE",
            sqlu"CREATE SCHEMA public",
            sqlu"GRANT ALL ON SCHEMA public TO postgres",
            sqlu"GRANT ALL ON SCHEMA public TO public",

            // Create schema
            schema.create,

            // Seed tables with fake data
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
                    Some(newPerson().getMiddleName()),
                    newPerson().getEmail(),
                    newPerson().getTelephoneNumber(),
                    newPerson().getAddress().getAddressLine1(),
                    Some(newPerson().getAddress().getAddressLine2()),
                    "NSW",
                    newPerson().getAddress().getPostalCode()
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
