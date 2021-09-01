object Seeder extends LogicMixin {
  private val isSeedingEnabled: Boolean = all(
    List(
      System.getenv("PROJECT_MODE") != "production",
      System.getenv("CONTROLLER_SEED_RUN") == "yes"
    )
  )

  final def apply() = {
    if (isSeedingEnabled) {
      UsersSeeder()
      ItemsSeeder()
    }
  }
}
