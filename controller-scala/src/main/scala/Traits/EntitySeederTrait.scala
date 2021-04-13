trait EntitySeederTrait extends SeederTrait {
  protected val count: Int = 0
  protected def clearData(): Unit
  protected def predefinedData(): Unit
  protected def seed(): Unit
  protected def apply(): Unit
}
