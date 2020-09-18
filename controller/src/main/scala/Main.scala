object Main {
    def run() = {
        Seeders.run()
        // println(UserValidators.isPasswordValid(password = "wow"))
        // println(UserValidators.isPasswordValid(password = "Benji123!"))
        Server.run(List())
    }
}
