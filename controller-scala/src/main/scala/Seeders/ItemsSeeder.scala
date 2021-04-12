import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.text.TextProducer

object ItemsSeeder extends SeederTrait {
  override final val count: Int = 15

  override final def clearData(): Unit = {
    ItemsService.delete(method = "hard-delete-all-rows")
  }

  override final def seed(): Unit = {
    def seedRow(): Unit = {
      val fairy: Fairy       = Fairy.create();
      val text: TextProducer = fairy.textProducer();

      val name: String =
        StringUtilities.toTitleCase(
          text.latinWord(randomGaussianDiscrete(min = 2, max = 15))
        )
      val key: String                     = createKey(name)
      val description: Option[String]     = MarkdownSeeder(text)
      val additionalNotes: Option[String] = MarkdownSeeder(text)

      ItemsService.create(
        key,
        name,
        description,
        additionalNotes
      )
    }

    count times seedRow()
  }

  override final def apply(): Unit = {
    clearData()
    predefinedData()
    seed()
  }
}
