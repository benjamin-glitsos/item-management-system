CREATE INDEX CONCURRENTLY users_last_name_trigram_index
ON users
USING gin(cast(last_name as text) gin_trgm_ops);
