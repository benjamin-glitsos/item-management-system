import java.io.IOException
import java.sql.SQLException

object Errors {
    def generalException(exception: Exception): Error = {
        val code = "general_exception"
        val message = exception.getMessage
        val field = None
        Error(code, message, field)
    }

    def databaseException(exception: SQLException): Error = {
        val code = "database_exception"
        val message = exception.getMessage
        val field = None
        Error(code, message, field)
    }

    def ioException(exception: IOException): Error = {
        val code = "io_exception"
        val message = exception.getMessage
        val field = None
        Error(code, message, field)
    }
}
