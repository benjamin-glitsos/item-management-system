import slick.jdbc.PostgresProfile.api._

object SexDAO extends TableQuery(new SexSchema(_)) {}
