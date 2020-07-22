import slick.jdbc.PostgresProfile.api._

object Loader extends Connection {
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
                SexDAO ++= SexLoader.data(),
                RecordsDAO += RecordsLoader.blank,
                PeopleDAO += PeopleLoader.admin,
                UsersDAO += UsersLoader.admin,
                RecordsDAO.filter(_.id === 1).update(RecordsLoader.admin),

                // Seed tables with randomised fake data
                RecordsDAO ++= RecordsLoader.data(),
                PeopleDAO ++= PeopleLoader.data(),
                UsersDAO ++= UsersLoader.data()
            )
        )
    }
}
