import slick.driver.PostgresDriver.api._

trait Connection {
    val db = Database.forURL(
        s"jdbc:postgresql://${System.getenv("POSTGRES_HOST")}/${System.getenv("POSTGRES_DB")}",
        driver = "org.postgresql.Driver",
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD")
    )
}
