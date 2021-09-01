import scala.concurrent.Future
import cats.data.ValidatedNec

trait ValidationMixin {
  type Validation[A] = Future[ValidatedNec[Error, A]]
}
