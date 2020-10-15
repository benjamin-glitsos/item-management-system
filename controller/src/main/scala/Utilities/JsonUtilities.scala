import io.circe.Json
import monocle.{Optional => MonocleOption}

trait JsonUtilities {
    def getter[A](json: Json, lens: MonocleOption[Json, A]): A = {
        lens.getOption(json).get
    }
}
