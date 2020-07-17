import slick.driver.PostgresDriver.api._

trait Queries {
    def withId(table: Table, id: Int) = {
        table.filter(_.id === id)
    }
}
