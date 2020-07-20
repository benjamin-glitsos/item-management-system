import slick.jdbc.PostgresProfile.api._

object PeopleDAO extends TableQuery(new PeopleSchema(_)) {}
