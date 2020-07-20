import slick.jdbc.PostgresProfile.api._

object Data extends Connection {
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
                SexDAO ++= SexData.data(),
                RecordsDAO += RecordsData.blank,
                PeopleDAO += PeopleData.admin,
                UsersDAO += UsersData.admin,
                RecordsDAO.filter(_.id === 1).update(RecordsData.admin), // TODO: create a function like withId

                // Seed tables with randomised fake data
                RecordsDAO ++= RecordsData.data(),
                PeopleDAO ++= PeopleData.data(),
                UsersDAO ++= UsersData.data()
            )
        )
    }
}
