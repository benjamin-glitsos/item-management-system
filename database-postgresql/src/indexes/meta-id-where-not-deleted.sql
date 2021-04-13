CREATE INDEX CONCURRENTLY meta_id_where_not_deleted_index
ON meta(id)
WHERE is_deleted IS false;
