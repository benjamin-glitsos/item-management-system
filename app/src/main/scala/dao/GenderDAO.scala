import slick.driver.PostgresDriver.api._

object GenderDAO extends TableQuery(new GenderSchema(_)) {
    val seedCount: Int = 2
}
