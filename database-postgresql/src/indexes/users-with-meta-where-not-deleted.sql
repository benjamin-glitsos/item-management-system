CREATE INDEX CONCURRENTLY users_with_meta_on_username_where_not_deleted_index
ON users_with_meta(username)
WHERE is_deleted = false;
