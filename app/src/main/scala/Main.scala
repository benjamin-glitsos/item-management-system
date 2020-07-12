object Main extends Database {
    def main(args: Array[String]) = {
        val setup = db.run(Users.dropCreate())
        // val select = db.run(users.result).foreach(println)
    }
}
