object Main extends Connection {
    def main(args: Array[String]): Unit = {
        val recreate = recreateSchema()
        val setup = db.run(Data.setup)
    }
}
