import io.circe.Json
import monocle.{Optional => MonocleOption}

trait JsonUtilities {
    def getter[A](json: Json, lens: MonocleOption[Json, A]): A = {
        lens.getOption(json).get
        // TODO: use case matching on A and dynamic (String) methods to take Json and String
    }
}
