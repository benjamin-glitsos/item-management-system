trait EnvUtilities {
    def getEnvBool(key: String): Boolean = {
        println(sys.env.getOrElse(key, "no"))
        sys.env.getOrElse(key, "no") == "yes"
    }
}
