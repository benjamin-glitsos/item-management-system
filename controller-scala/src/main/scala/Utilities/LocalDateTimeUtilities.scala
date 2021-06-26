import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

object LocalDateTimeUtilities {
  private val dateFormat: String = "yyyy-MM-dd-HH-mm-ss.zzz"

  private val formatter: DateTimeFormatter =
    DateTimeFormat.forPattern(dateFormat);

  final def parse(s: String): LocalDateTime = LocalDateTime.parse(s, formatter)

  final def format(d: LocalDateTime): String = d.toString(dateFormat)
}
