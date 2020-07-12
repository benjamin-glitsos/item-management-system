import slick.driver.PostgresDriver.api._

object Records extends Seeder {
    type Record = (Int, String, Int, String, Int, String, Int)

    class RecordsTable(tag: Tag) extends Table[Record](tag, "records") {
        def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
        def created_at = column[String]("created_at")
        def created_by = column[Int]("created_by")
        def updated_at = column[String]("updated_at")
        def updated_by = column[Int]("updated_by")
        def deleted_at = column[String]("deleted_at")
        def deleted_by = column[Int]("deleted_by")
        def * = (
            id,
            created_at,
            created_by,
            updated_at,
            updated_by,
            deleted_at,
            deleted_by
        )
        // def created_by_fk = foreignKey("created_by_fk", created_by, Users.users)(_.id)
        // def updated_by_fk = foreignKey("updated_by_fk", updated_by, Users.users)(_.id)
        // def deleted_by_fk = foreignKey("deleted_by_fk", deleted_by, Users.users)(_.id)
    }

    val records = TableQuery[RecordsTable]

    def initialise() = DBIO.seq(
        records.schema.drop,
        records.schema.create,
        records ++= seed[Record](
            1,
            10,
            (
                0,
                "TEST2",
                0,
                "TEST2",
                0,
                "TEST2",
                0,
                )
            )
        )
}
