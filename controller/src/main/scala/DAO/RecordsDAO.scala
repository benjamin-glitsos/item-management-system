object RecordsDAO {
    def upsert(record: RecordEdit) = {
        sql"""
        INSERT INTO records (uuid, created_at, created_by)
        VALUES (${record.uuid}, NOW(), ${record.user_id})
        ON CONFLICT (uuid)
        DO UPDATE SET
              updated_at = EXCLUDED.created_at
            , updated_by = EXCLUDED.created_by
        RETURNING id, updated_by
        """.query[RecordReturn].unique
    }
}
