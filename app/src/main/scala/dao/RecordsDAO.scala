import slick.driver.PostgresDriver.api._

object RecordsDAO extends TableQuery(new RecordsSchema(_)) {
    val seedCount: Int = 6
}
