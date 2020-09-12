object UserErrors with ErrorUtilities {
    private def code(n) = generateCode(sys.env.getOrElse("USERS_TABLE", "users"), n)

    val userDoesntExist = Error(code(1), "No user with that username exists.")
}
