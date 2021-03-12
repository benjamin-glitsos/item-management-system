object Seeder {
  final def apply() = {
    if (System.getenv("PROJECT_MODE") != "production") {
      UsersSeeder()
    }
  }
}
