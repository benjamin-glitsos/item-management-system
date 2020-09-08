import org.http4s.dsl.io._

package bundles {
    object http4s {
        object MaybeNumber extends OptionalQueryParamDecoderMatcher[Int]("number")
        object MaybeLength extends OptionalQueryParamDecoderMatcher[Int]("length")
    }
}
