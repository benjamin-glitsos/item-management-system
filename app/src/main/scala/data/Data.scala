import java.sql.Timestamp
import slick.driver.PostgresDriver.api._
import SchemaTypes._

object Data extends Connection with Seeder with Queries {
    val schema = RecordsDAO.schema ++ PeopleDAO.schema ++ UsersDAO.schema

    def setup() = {
        request(
            DBIO.seq(
                // Reset database schema to blank state
                sqlu"DROP SCHEMA public CASCADE",
                sqlu"CREATE SCHEMA public",
                sqlu"GRANT ALL ON SCHEMA public TO postgres",
                sqlu"GRANT ALL ON SCHEMA public TO public",

                // Create schema
                schema.create,

                // Create the admin user and handle the circular foreign keys
                RecordsDAO += (id, None, None, None, None, None, None),
                PeopleDAO += (
                    id,
                    1,
                    System.getenv("ADMIN_FIRST_NAME"),
                    System.getenv("ADMIN_LAST_NAME"),
                    Some(System.getenv("ADMIN_MIDDLE_NAME")),
                    randFK(GendersDAO.seedCount),
                    System.getenv("ADMIN_EMAIL"),
                    System.getenv("ADMIN_PHONE"),
                    System.getenv("ADMIN_ADDRESS_LINE_1"),
                    System.getenv("ADMIN_ADDRESS_LINE_2"),
                    System.getenv("ADMIN_ZIP")
                ),
                withId(RecordsDAO, 1).update((
                    1,
                    Some(currentTimestamp()),
                    Some(randFK(UsersDAO.seedCount)),
                    Some(currentTimestamp()),
                    Some(randFK(UsersDAO.seedCount)),
                    Some(currentTimestamp()),
                    Some(randFK(UsersDAO.seedCount))
                )),
                UsersDAO += (
                    id,
                    1,
                    System.getenv("ADMIN_USERNAME"),
                    System.getenv("ADMIN_PASSWORD")
                ),

                // Populate static data
                GenderDAO ++= Seq(
                    (id, "Male"),
                    (id, "Female")
                ),

                // Seed tables with fake data
                RecordsDAO ++= seed[Record](
                    RecordsDAO.seedCount,
                    (
                        id,
                        Some(currentTimestamp()),
                        Some(1),
                        Some(currentTimestamp()),
                        Some(1),
                        Some(currentTimestamp()),
                        Some(1)
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
                        newPerson().getAddress().getCity(),
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
                )
            )
        )
    }
}
