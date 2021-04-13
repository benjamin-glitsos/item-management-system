CREATE INDEX CONCURRENTLY users_username_where_not_deleted_index
ON users(username)
WHERE is_deleted = false;
