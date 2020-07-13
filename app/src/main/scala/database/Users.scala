import slick.driver.PostgresDriver.api._
// import slick.jdbc.meta.MTable
import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._

object Users extends Seeder {
    type User = (Int, String, String, Int)

    val seedCount: Int Refined Positive = 4

    class UsersTable(tag: Tag) extends Table[User](tag, "users") {
        def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
        def username = column[String]("username")
        def password = column[String]("password")
        def records_id = column[Int]("records_id")
        def * = (id, username, password, records_id)
        def records_fk = foreignKey("results_fk", id, Records.records)(_.id)
    }

    val users = TableQuery[UsersTable]

    def initialise() = DBIO.seq(
        // users.schema.drop,
        users.schema.create,
        users ++= seed[User](
            seedCount,
            (0, newPerson().getUsername(), newPerson().getPassword(), 1)
        )
    )
}
