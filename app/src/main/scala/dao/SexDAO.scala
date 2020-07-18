import slick.driver.PostgresDriver.api._

object SexDAO extends TableQuery(new SexSchema(_)) with Seeder {
    val seedCount: Int = 2
}
