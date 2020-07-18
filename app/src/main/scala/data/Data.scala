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
                PeopleDAO += Person(
                    id = id,
                    record_id = 1,
                    first_name = System.getenv("ADMIN_FIRST_NAME"),
                    last_name = System.getenv("ADMIN_LAST_NAME"),
                    other_names = Some(System.getenv("ADMIN_MIDDLE_NAME")),
                    sex_id = System.getenv("ADMIN_SEX").toInt,
                    email_address = System.getenv("ADMIN_EMAIL"),
                    phone_number = System.getenv("ADMIN_PHONE"),
                    address_line_one = System.getenv("ADMIN_ADDRESS_LINE_1"),
                    address_line_two = System.getenv("ADMIN_ADDRESS_LINE_2"),
                    zip = System.getenv("ADMIN_ZIP")
                ),
                UsersDAO += User(
                    id = id,
                    person_id = 1,
                    username = System.getenv("ADMIN_USERNAME"),
                    password = System.getenv("ADMIN_PASSWORD")
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
                    Person(
                        id = id,
                        record_id = randFK(RecordsDAO.seedCount),
                        first_name = newPerson().getFirstName(),
                        last_name = newPerson().getLastName(),
                        other_names = Some(newPerson().getMiddleName()),
                        sex_id = randFK(SexDAO.seedCount), // TODO: not randomising?
                        email_address = newPerson().getEmail(),
                        phone_number = newPerson().getTelephoneNumber(),
                        address_line_one = newPerson().getAddress().getAddressLine1(),
                        address_line_two = newPerson().getAddress().getCity(),
                        zip = newPerson().getAddress().getPostalCode()
                    )
                ),
                UsersDAO ++= seed[User](
                    UsersDAO.seedCount,
                    User(
                        id = id,
                        person_id = randFK(PeopleDAO.seedCount),
                        username = newPerson().getUsername(),
                        password = newPerson().getPassword()
                    )
                )
            )
        )
    }
}
