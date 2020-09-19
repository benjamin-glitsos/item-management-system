trait EnvUtilities {
    def getEnvBool(key: String): Boolean = {
        sys.env.getOrElse(key, "no") == "yes"
    }
}
