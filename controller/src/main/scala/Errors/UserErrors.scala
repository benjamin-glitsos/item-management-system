object UserErrors with ErrorUtilities {
    private def code(n) = generateCode(sys.env.getOrElse("USERS_TABLE", "users"), n)

    def userDoesntExist(username: String) = {
        Error(code(1), s"No user with the username '${username}' exists.")
    }
}
