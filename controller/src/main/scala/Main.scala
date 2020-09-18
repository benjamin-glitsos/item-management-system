object Main extends LoggingUtilities {
    def run() = {
        Seeders.script()

        // TODO: write at least one test case for MVP
        logSmallHeading("Starting server")
        // println(UserValidators.isPasswordValid(password = "wow"))
        // println(UserValidators.isPasswordValid(password = "Benji123!"))
        Server.run(List())
    }
}
