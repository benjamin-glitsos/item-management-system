object Seeder {
  final def apply() = {
    if (System.getenv("CONTROLLER_SEED_RUN") == "yes") {
      UsersSeeder()
      ItemsSeeder()
    }
  }
}
