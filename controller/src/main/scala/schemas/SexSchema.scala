import slick.jdbc.PostgresProfile.api._

class SexSchema(tag: Tag) extends Table[Sex](tag, System.getenv("SEX_NAME")) {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (id, name) <> (Sex.tupled, Sex.unapply)
}
