import slick.driver.PostgresDriver.api._
import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._

object Users extends Seeder {
    type User = (Int, Int, String, String)

    val seedCount: Int Refined Positive = 4

    class UsersTable(tag: Tag) extends Table[User](tag, "users") {
        def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
        def record_id = column[Int]("record_id")
        def username = column[String]("username")
        def password = column[String]("password")
        def * = (id, username, password)
    }
    def record_fk = foreignKey("record_fk", record_id, Records.records)(_.id)

    val users = TableQuery[UsersTable]

    def initialise() = DBIO.seq(
        users.schema.create,
        users ++= seed[User](
            seedCount,
            (
                0,
                randFK(Records.seedCount),
                newPerson().getUsername(),
                newPerson().getPassword()
            )
        )
    )

    def dropAllTables: DBIO[Unit] =
      sqlu"""
          DROP SCHEMA public CASCADE;
          CREATE SCHEMA public;
          GRANT ALL ON SCHEMA public TO postgres;
          GRANT ALL ON SCHEMA public TO public;
          COMMENT ON SCHEMA public IS 'standard public schema';
      """
}
