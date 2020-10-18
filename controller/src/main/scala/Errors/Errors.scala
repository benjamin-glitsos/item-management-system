import java.io.IOException
import java.sql.SQLException

object Errors {
    def unexpectedError(): Error = {
        val code = "unexpected_error"
        val message = "An unexpected error occured."
        val field = None
        Error(code, message, field)
    }

    def generalException(message: String): Error = {
        val code = "general_exception"
        val field = None
        Error(code, message, field)
    }

    def databaseException(message: String): Error = {
        val code = "database_exception"
        val field = None
        Error(code, message, field)
    }

    def ioException(message: String): Error = {
        val code = "io_exception"
        val field = None
        Error(code, message, field)
    }

    def resourceNotFound(): Error = {
        val code = "resource_not_found"
        val message = "The page or other kind of resource that you have requested cannot be found at that URI."
        val field = None
        Error(code, message, field)
    }
}
