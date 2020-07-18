import slick.driver.PostgresDriver.api._
import SchemaTypes._

// case class Sex(id: Int, name: String)

class SexSchema(tag: Tag) extends Table[Sex](tag, "sex") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (id, name) // <> (Sex.tupled, Sex.unapply)
}
