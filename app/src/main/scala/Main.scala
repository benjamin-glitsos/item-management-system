object Main extends Database {
    def main(args: Array[String]) = {
        val setup = initialise(
            Users.initialise(),
            Records.initialise()
        )
    }
}
