object Loaders {
    def load(count: Int, nameEnv: String, nameAlt: String, fn: => Unit) = {
        (1 to count) foreach (x => fn)
        println(s"Populated ${sys.env.getOrElse(nameEnv, nameAlt)}")
    }

    def run() = {
        load(
            count = 15,
            nameEnv = "USERS_TABLE",
            nameAlt = "users",
            fn = UsersLoader.create()
        )
    }
}
