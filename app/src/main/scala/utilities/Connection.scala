import slick.driver.PostgresDriver.api._

trait Connection {
    val db = Database.forConfig("postgres")
}
