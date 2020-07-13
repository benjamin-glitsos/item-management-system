object Main extends Database {
    def main(args: Array[String]): Unit = {
        // val clear = Users.dropAllTables()
        val setup = initialise(
            Users.initialise(),
            Records.initialise()
        )
    }
}
