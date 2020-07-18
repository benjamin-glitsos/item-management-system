import slick.driver.PostgresDriver.api._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {
    def all(): Future[Option[Iterable[User]]] = {
        db.run(this.result).map(_.headOption)
    }
}
