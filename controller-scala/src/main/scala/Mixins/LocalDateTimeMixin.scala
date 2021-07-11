import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeMixin {
  private val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");

  final def parse(s: String): LocalDateTime = LocalDateTime.parse(s, formatter)
}
