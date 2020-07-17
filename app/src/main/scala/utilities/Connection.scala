import scala.util.{Success,Failure}
import scala.concurrent._
import ExecutionContext.Implicits.global
import slick.driver.PostgresDriver.api._

trait Connection {
    val db = Database.forConfig("postgres")

    def request(action: DBIO[Any]) = {
        db.run(action.asTry).map {
            case Failure(ex) => {
                println(s"error : ${ex.getMessage}")
                None
            }
            case Success(x) => x
        }
    }
}
