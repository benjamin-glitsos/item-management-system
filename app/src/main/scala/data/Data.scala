import java.sql.Timestamp
import slick.driver.PostgresDriver.api._
import SchemaTypes._

object Data extends Connection with Seeder with Queries {
    val schema = SexDAO.schema ++ RecordsDAO.schema ++ PeopleDAO.schema ++ UsersDAO.schema

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

                // Create all predefined data
                SexDAO ++= Seq(
                    Sex(id, "Male"),
                    Sex(id, "Female")
                ),
                RecordsDAO += Record(id, None, None, None, None, None, None),
                PeopleDAO += (
                    id,
                    1,
                    System.getenv("ADMIN_FIRST_NAME"),
                    System.getenv("ADMIN_LAST_NAME"),
                    Some(System.getenv("ADMIN_MIDDLE_NAME")),
                    System.getenv("ADMIN_SEX").toInt,
                    System.getenv("ADMIN_EMAIL"),
                    System.getenv("ADMIN_PHONE"),
                    System.getenv("ADMIN_ADDRESS_LINE_1"),
                    System.getenv("ADMIN_ADDRESS_LINE_2"),
                    System.getenv("ADMIN_ZIP")
                ),
                UsersDAO += (
                    id,
                    1,
                    System.getenv("ADMIN_USERNAME"),
                    System.getenv("ADMIN_PASSWORD")
                ),
                RecordsDAO.filter(_.id === 1).update(Record(
                    id = 1,
                    created_at = Some(currentTimestamp()),
                    created_by = Some(1),
                    updated_at = Some(currentTimestamp()),
                    updated_by = Some(1),
                    deleted_at = Some(currentTimestamp()),
                    deleted_by = Some(1)
                )),

                // Seed tables with randomised fake data
                RecordsDAO ++= seed[Record](
                    RecordsDAO.seedCount,
                    Record(
                        id = id,
                        created_at = Some(currentTimestamp()),
                        created_by = Some(1),
                        updated_at = Some(currentTimestamp()),
                        updated_by = Some(1),
                        deleted_at = Some(currentTimestamp()),
                        deleted_by = Some(1)
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
                        randFK(SexDAO.seedCount), // TODO: not randomising?
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
