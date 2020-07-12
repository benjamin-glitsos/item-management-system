object Main extends Database {
    def main(args: Array[String]) = {
        val setup = run(
            Users.initialise(),
            Records.initialise()
        )
    }
}
