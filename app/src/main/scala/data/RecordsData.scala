object RecordsData extends Seeder {
    val seedCount: Int = 6

    val blank: Record = Record(id, None, None, None, None, None, None)

    val admin: Record = Record(
        id = 1,
        created_at = Some(currentTimestamp()),
        created_by = Some(1),
        updated_at = Some(currentTimestamp()),
        updated_by = Some(1),
        deleted_at = Some(currentTimestamp()),
        deleted_by = Some(1)
    )

    def data() = {
        seeder[Record](seedCount, admin)
    }
}
