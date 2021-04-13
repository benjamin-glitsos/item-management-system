CREATE INDEX CONCURRENTLY items_key_where_not_deleted_index
ON items(key)
WHERE is_deleted = false;
