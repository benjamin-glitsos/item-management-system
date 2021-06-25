import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeUtilities {
  final def parse(s: String): LocalDateTime = {
    val format: DateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
    LocalDateTime.parse(s, format)
  }
}
