object Seeder {
  final def apply() = {
    if (
      System.getenv("PROJECT_MODE") != "production" || System.getenv(
        "SEED_RUN"
      ) == "yes"
    ) {
      UsersSeeder()
    }
  }
}
