import java.util.Date
import java.text.SimpleDateFormat

object DateUtilities {
  final def parse(s: String): Date = {
    val format: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    format.parse(s)
  }
}
