import java.util.Date
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.text.TextProducer

object ItemsSeeder
    extends SeederMixin
    with MarkdownSeederMixin
    with DateMixin
    with StringMixin {
  override final val count: Int = 15

  override final def reset(): Unit =
    ItemsService.delete(method = "hard-delete-all-rows")

  override final def defaults(): Unit = {
    val fairy: Fairy       = Fairy.create();
    val text: TextProducer = fairy.textProducer();

    ItemsService.create(
      sku = System.getenv("DEMO_ITEM_SKU"),
      upc = System.getenv("DEMO_ITEM_UPC"),
      name = System.getenv("DEMO_ITEM_NAME"),
      description = generateMarkdown(text),
      acquisitionDate = dateParse(System.getenv("DEMO_ITEM_ACQUISITION_DATE")),
      expirationDate =
        sys.env.get("DEMO_ITEM_EXPIRATION_DATE").map(dateParse(_)),
      unitCost = System.getenv("DEMO_ITEM_UNIT_COST").toDouble,
      unitPrice = sys.env.get("DEMO_ITEM_UNIT_PRICE").map(_.toDouble),
      quantityAvailable = System.getenv("DEMO_ITEM_QUANTITY_AVAILABLE").toInt,
      quantitySold = System.getenv("DEMO_ITEM_QUANTITY_SOLD").toInt,
      additionalNotes = generateMarkdown(text)
    )
  }

  override final def seed(): Unit = {
    val fairy: Fairy           = Fairy.create()
    val text: TextProducer     = fairy.textProducer()
    val seedIsForSale: Boolean = biasedCoinFlip(0.75)
    val seedName: String = toTitleCase(
      text.latinWord(randomGaussianDiscrete(min = 2, max = 15))
    )
    val seedUnitCost: Double = randomCurrency()
    val seedUnitPrice: Option[Double] =
      Option.when(seedIsForSale)(seedUnitCost * (1 + randomDouble))

    val sku: String                 = randomSku(seedName)
    val upc: String                 = randomUpc()
    val name: String                = seedName
    val description: Option[String] = generateMarkdown(text)
    val acquisitionDate: Date       = randomDateBetween(yearsAgo(10), new Date())
    val expirationDate: Option[Date] =
      Option.when(coinFlip)(
        addRandomDays(date = acquisitionDate, min = 0, max = 10 * 365)
      )
    val unitCost: Double          = seedUnitCost
    val unitPrice: Option[Double] = seedUnitPrice
    val quantityAvailable: Int    = randomBetween(0, 20)
    val quantitySold: Int =
      if (seedIsForSale) randomBetween(0, 100) else 0
    val additionalNotes: Option[String] = generateMarkdown(text)

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

  override final def apply(): Unit = {
    reset()
    defaults()
    count times seed()
  }
}
