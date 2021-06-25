import java.util.Date
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.text.TextProducer

object ItemsSeeder extends EntitySeederTrait {
  override final val count: Int = 15

  final def clearData(): Unit = {
    ItemsService.delete(method = "hard-delete-all-rows")
  }

  final def predefinedData(): Unit = if (
    System.getenv("PROJECT_MODE") != "production"
  ) {
    val fairy: Fairy       = Fairy.create();
    val text: TextProducer = fairy.textProducer();

    ItemsService.create(
      sku = System.getenv("DEMO_ITEM_SKU"),
      upc = System.getenv("DEMO_ITEM_UPC"),
      name = System.getenv("DEMO_ITEM_NAME"),
      description = MarkdownSeeder(text),
      acquisitionDate =
        DateUtilities.parse(System.getenv("DEMO_ITEM_ACQUISITION_DATE")),
      expirationDate =
        sys.env.get("DEMO_ITEM_EXPIRATION_DATE").map(DateUtilities.parse(_)),
      unitCost = System.getenv("DEMO_ITEM_UNIT_COST").toDouble,
      unitPrice = sys.env.get("DEMO_ITEM_UNIT_PRICE").map(_.toDouble),
      quantityAvailable = System.getenv("DEMO_ITEM_QUANTITY_AVAILABLE").toInt,
      quantitySold = System.getenv("DEMO_ITEM_QUANTITY_SOLD").toInt,
      additionalNotes = MarkdownSeeder(text)
    )
  }

  final def seed(): Unit = {
    def seedRow(): Unit = {
      val fairy: Fairy       = Fairy.create()
      val text: TextProducer = fairy.textProducer()

      val seed_name: String = StringUtilities.toTitleCase(
        text.latinWord(randomGaussianDiscrete(min = 2, max = 15))
      )

      val sku: String                     = createSku(seed_name)
      val upc: String                     = createUpc()
      val name: String                    = seed_name
      val description: Option[String]     = MarkdownSeeder(text)
      val acquisitionDate: Date           = DateUtilities.parse("2021-01-01")
      val expirationDate: Option[Date]    = None
      val unitCost: Double                = "1.00".toDouble
      val unitPrice: Option[Double]       = None
      val quantityAvailable: Int          = 0
      val quantitySold: Int               = 0
      val additionalNotes: Option[String] = MarkdownSeeder(text)

      ItemsService.create(
        sku,
        upc,
        name,
        description,
        acquisitionDate,
        expirationDate,
        unitCost,
        unitPrice,
        quantityAvailable,
        quantitySold,
        additionalNotes
      )
    }

    count times seedRow()
  }

  final def apply(): Unit = {
    clearData()
    predefinedData()
    seed()
  }
}
