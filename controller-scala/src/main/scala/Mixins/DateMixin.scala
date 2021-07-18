import java.util.Date
import java.text.SimpleDateFormat

trait DateMixin {
  private val formatter: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

  final def dateParse(s: String): Date = formatter.parse(s)

  final def dateFormat(d: Date): String = formatter.format(d)
}
