import scala.util.{Success,Failure}
import scala.concurrent._
import ExecutionContext.Implicits.global
import slick.jdbc.PostgresProfile.api._

trait Connection {
    val db = Database.forConfig("database")

    def request(action: DBIO[Any]) = {
        db.run(action.asTry).map {
            case Failure(ex) => {
                println(s"Error: ${ex.getMessage}")
                None
            }
            case Success(x) => x
        }
    }
}
