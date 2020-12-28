object MissingCookieError {
  def apply(cookieName: String): Error = {
    Error("missing_cookie", s"The cookie '$cookieName' is missing.")
  }
}
