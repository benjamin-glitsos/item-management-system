import slick.driver.PostgresDriver.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) {
    val seedCount: Int = 4
}
