import java.sql.Timestamp
import slick.driver.PostgresDriver.api._

object Data extends Connection with Queries {
    val DAOs: List[Table] = List(SexDAO, RecordsDAO, PeopleDAO, UsersDAO)

    // val schema = SexDAO.schema ++ RecordsDAO.schema ++ PeopleDAO.schema ++ UsersDAO.schema

    def setup() = {
        request(
            DBIO.seq(
                // Reset database schema to blank state
                dropSchema(),

                // Create schema
                DAOs.map(_.schema).fold{ (a, b) => a ++ b }.create,

                // Create all predefined data
                SexDAO ++= SexData.data,
                RecordsDAO += RecordsData.blank,
                PeopleDAO += PeopleData.admin,
                UsersDAO += UsersData.admin,
                RecordsDAO.filter(_.id === 1).update(RecordsData.admin), // TODO: create a function like withId

                // Seed tables with randomised fake data
                RecordsDAO ++= RecordsData.seed,
                PeopleDAO ++= PeopleData.seed,
                UsersDAO ++= UsersData.seed
            )
        )
    }
}
