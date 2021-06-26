import java.util.Date
import java.text.SimpleDateFormat

object DateUtilities {
  private val formatter: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

  final def parse(s: String): Date = formatter.parse(s)

  final def format(d: Date): String = formatter.format(d)
}
