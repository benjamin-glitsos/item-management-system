import slick.driver.PostgresDriver.api._
import SchemaTypes._

class SexSchema(tag: Tag) extends Table[Gender](tag, "gender") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (id, name) <> (Sex.tupled, Sex.unapply)
}
