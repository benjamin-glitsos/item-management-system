object Main extends Database {
    def main(args: Array[String]): Unit = {
        val setup = initialise(
            Users.initialise(),
            Records.initialise()
        )
    }
}
