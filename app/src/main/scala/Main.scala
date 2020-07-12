import slick.driver.PostgresDriver.api._

object Main extends Database {
    def main(args: Array[String]) = {
        val setup = db.run(
            DBIO.sequence(
                Records.initialise(),
                Users.initialise()
            )
        )
    }
}
