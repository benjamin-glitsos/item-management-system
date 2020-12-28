import scala.util.Random
import com.devskiller.jfairy.producer.text.TextProducer

object MarkdownIpsum extends SeederTrait {
  private final def hasContent()         = biasedCoinFlip(probability = 0.75)
  private final def numberOfParagraphs() = Random.between(1, 2)
  private final def numberOfSentences()  = Random.between(1, 3)
  private final def hasHeading()         = biasedCoinFlip(0.75)

  final def apply(textProducer: TextProducer): String = {
    if (hasContent()) {
      (1 to numberOfParagraphs)
        .map(paragraph => {
          val heading: Option[String] =
            if (hasHeading()) {
              Some(s"## ${textProducer.latinSentence(1)}")
            } else {
              None
            }
          val content: String = textProducer.latinSentence(numberOfSentences())
          heading ++ List(content)
        })
        .flatten
        .mkString("\n\n")
    } else {
      ""
    }
  }
}
