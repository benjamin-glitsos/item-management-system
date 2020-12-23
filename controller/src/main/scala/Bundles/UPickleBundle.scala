import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._

package upickle_bundle {
  object implicits {
    implicit val rwLocalDateTime: ReadWriter[LocalDateTime] =
      readwriter[ujson.Value].bimap[LocalDateTime](
          x => x.toString(),
          json => {
            val defaultFormat: DateTimeFormatter =
              DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss.zzz");
            LocalDateTime.parse(json.toString(), defaultFormat)
          }
      )
    implicit val rwUsersWithMeta: ReadWriter[UsersWithMeta] = macroRW
  }
}
