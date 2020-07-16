import slick.driver.PostgresDriver.api._

object PeopleDAO extends TableQuery(new RecordsSchema(_)) {
    val seedCount: Int = UsersDAO.seedCount + 2
}
