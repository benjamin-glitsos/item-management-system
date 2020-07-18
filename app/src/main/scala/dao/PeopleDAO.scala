import slick.driver.PostgresDriver.api._

object PeopleDAO extends TableQuery(new PeopleSchema(_)) {}
