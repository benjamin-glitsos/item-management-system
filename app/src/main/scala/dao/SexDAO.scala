import slick.driver.PostgresDriver.api._

object SexDAO extends TableQuery(new SexSchema(_)) {}
