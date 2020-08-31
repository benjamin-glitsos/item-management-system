trait DoobieImports {
    import doobie._
    import doobie.implicits._
    import doobie.util.ExecutionContexts
    import doobie.postgres._
    import doobie.postgres.implicits._

    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )
}
