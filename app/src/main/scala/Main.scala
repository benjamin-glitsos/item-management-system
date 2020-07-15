object Main extends Connection {
    def main(args: Array[String]): Unit = {
        val setup = db.run(Data.setup)
    }
}
