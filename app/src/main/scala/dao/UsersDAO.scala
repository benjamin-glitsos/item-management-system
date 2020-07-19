import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import io.circe.syntax._

object UsersDAO extends TableQuery(new UsersSchema(_)) with Connection {
    // def findById(id: Int): Future[Option[User]] = {
    //     db.run(this.filter(_.id === id).result).map(_.headOption)
    // }

    def all() = {
        db.run(this.result)
    }
}
