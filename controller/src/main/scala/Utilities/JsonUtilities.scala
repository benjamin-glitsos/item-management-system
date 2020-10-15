import io.circe.Json
import monocle.{Optional => MonocleOption}

trait JsonUtilities {
    def jsonGet[A](lens: MonocleOption[Json, A], json: Json): A = {
        lens.getOption(json).get
    }
}
