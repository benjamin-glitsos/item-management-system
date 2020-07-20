import slick.jdbc.PostgresProfile.api._

object RecordsDAO extends TableQuery(new RecordsSchema(_)) {}
