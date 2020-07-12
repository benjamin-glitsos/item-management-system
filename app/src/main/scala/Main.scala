object Main extends Database {
    def main(args: Array[String]) = {
        val setup = db.run(Users.dropCreate())
        val setup2 = db.run(Users.seed())
    }
}
