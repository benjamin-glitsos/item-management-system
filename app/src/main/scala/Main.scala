object Main extends Database {
    def main(args: Array[String]) = {
        val setup = db.run(Users.initialise())
    }
}
