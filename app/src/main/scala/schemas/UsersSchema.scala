import slick.jdbc.PostgresProfile.api._

class UsersSchema(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def person_id = column[Int]("person_id")
    def username = column[String]("username")
    def password = column[String]("password")
    def * = (
        id,
        person_id,
        username,
        password
    ) <> (User.tupled, User.unapply)
    def people_fk = foreignKey("people_fk", person_id, PeopleDAO)(_.id)
}
