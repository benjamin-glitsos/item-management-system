import slick.driver.PostgresDriver.api._

object Data extends Seeder {
    def run(): Unit = {
        DBIO.seq(
            RecordsDAO.schema.create,
            RecordsDAO ++= generate[Types.Record](
                RecordsDAO.seedCount,
                (
                    id,
                    new Timestamp(System.currentTimeMillis()),
                    randFK(UsersDAO.seedCount),
                    new Timestamp(System.currentTimeMillis()),
                    randFK(UsersDAO.seedCount),
                    new Timestamp(System.currentTimeMillis()),
                    randFK(UsersDAO.seedCount)
                )
            ),
            UsersDAO.schema.create,
            UsersDAO ++= generate[Types.User](
                UsersDAO.seedCount,
                (
                    id,
                    randFK(RecordsDAO.seedCount),
                    newPerson().getUsername(),
                    newPerson().getPassword()
                )
            )
        )
    }
}
