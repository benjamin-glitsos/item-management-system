object Seeder {
  final def apply() = {
    if (
      System.getenv("PROJECT_MODE") != "production" || System.getenv(
        "CONTROLLER_SEED_RUN"
      ) == "yes"
    ) {
      UsersSeeder()
    }
  }
}
