import scala.concurrent._

object UsersService {
    def list(rows: Int, page: Int) = UsersDAO.list(rows, page)

    def show(id: Int) = UsersDAO.show(id)

    def delete(id: Int) = UsersDAO.delete(id)
}
