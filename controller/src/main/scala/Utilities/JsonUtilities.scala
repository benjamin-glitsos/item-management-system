import io.circe.Json

trait JsonUtilities {
    def jsonGet[A](lens: monocle.Optional[Json, A], json: Json): A = {
        lens.getOption(json).get
    }
}
