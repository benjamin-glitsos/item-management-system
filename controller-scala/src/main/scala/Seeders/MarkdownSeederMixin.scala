import scala.math.{min, max}
import com.devskiller.jfairy.producer.text.TextProducer

trait MarkdownSeederMixin extends SeederMixin {
  private final def hasContent(): Boolean = biasedCoinFlip(probability = 0.75)
  private final def numberOfParagraphs(): Int =
    randomGaussianDiscrete(min = 1, max = 3, mean = 1)
  private final def numberOfSentences(): Int =
    randomGaussianDiscrete(min = 1, max = 7, mean = 2)
  private final def hasHeading(): Boolean = biasedCoinFlip(probability = 0.5)
  private final def hasEmphasis(): Boolean =
    biasedCoinFlip(probability = 0.33)
  private final def isItalicVersusBold(): Boolean =
    biasedCoinFlip(probability = 0.5)

  private final def emphasiseSentence(sentence: String): String =
    if (hasEmphasis()) {
      def addEmphasis(text: String) = {
        val emphasis: String = if (isItalicVersusBold()) {
          "*"
        } else {
          "**"
        }
        emphasis + text + emphasis
      }

      val tokenisedSentence: List[String] =
        sentence.split(" ").filter(_.nonEmpty).toList

      val emphasisedSentence: List[String] = {
        val sentenceLength: Int = tokenisedSentence.length
        val replacedLength: Int =
          randomGaussianDiscrete(1, min(3, sentenceLength))
        val replacedIndex: Int =
          randomGaussianDiscrete(1, sentenceLength - replacedLength)

        def emphasiseTokens(tokens: List[String]): List[String] = List(
          addEmphasis(tokens.mkString(" "))
        )

        tokenisedSentence.patch(
          replacedIndex,
          emphasiseTokens(
            tokenisedSentence
              .slice(replacedIndex, replacedIndex + replacedLength)
          ),
          replacedLength
        )
      }

      emphasisedSentence.mkString(" ")
    } else {
      sentence
    }

  final def generateMarkdown(textProducer: TextProducer): Option[String] = {
    if (hasContent()) {
      val hasHeadings: Boolean = hasHeading()

      Some(
        (1 to max(1, numberOfParagraphs))
          .map(paragraph => {
            val heading: Option[String] = Option
              .when(hasHeadings) { s"## ${textProducer.latinSentence(1)}" }

            val content: String =
              emphasiseSentence(textProducer.latinSentence(numberOfSentences()))

            heading ++ List(content)
          })
          .flatten
          .mkString("\n\n")
      )

    } else {
      None
    }
  }
}
