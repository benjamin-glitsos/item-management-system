import slick.driver.PostgresDriver.api._

trait Queries {
    def dropSchema(): DBIO[Unit] = {
        sqlu"DROP SCHEMA public CASCADE",
        sqlu"CREATE SCHEMA public",
        sqlu"GRANT ALL ON SCHEMA public TO postgres",
        sqlu"GRANT ALL ON SCHEMA public TO public",
    }

    // def withId(table: Table, id: Int) = {
    //     table.filter(_.id === id)
    // }
}
