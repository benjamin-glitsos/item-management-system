object NotFoundError {
  def apply(): Error = {
    Error("not_found", "The requested resource was not found.")
  }
}
