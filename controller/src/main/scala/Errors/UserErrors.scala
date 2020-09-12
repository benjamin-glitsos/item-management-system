object UserErrors extends ErrorUtilities {
    private def code(n: Int) = generateCode(sys.env.getOrElse("USERS_TABLE", "users"), n)

    private def usernameLengthDescription(username: String) = s"The provided username (${username}) is ${username.length} characters in length."

    def userDoesntExist(username: String) = {
        Error(code(1), s"No user with the username '${username}' exists.")
    }

    def usernameTooShort(username: String, min: Int) = {
        Error(code(2), s"The username provided is underneath the minimum of ${min} characters in length. ${usernameLengthDescription(username)}")
    }

    def usernameTooLong(username: String, max: Int) = {
        Error(code(3), s"The username provided is above the maximum of ${max} characters in length. ${usernameLengthDescription(username)}")
    }
}
