import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

trait LocalDateTimeMixin {
  private val localDateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");

  final def localDateTimeParse(s: String): LocalDateTime =
    LocalDateTime.parse(s, localDateTimeFormatter)
}
