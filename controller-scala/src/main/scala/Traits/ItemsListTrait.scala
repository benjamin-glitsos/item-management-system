import java.time.LocalDateTime

trait ItemsListTrait {
  final type ItemsListAll = (
      Int,
      Int,
      Int,
      Int,
      String,
      String,
      Option[String],
      LocalDateTime,
      Option[LocalDateTime]
  )
}
